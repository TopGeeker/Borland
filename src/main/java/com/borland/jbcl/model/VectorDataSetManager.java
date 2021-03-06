/**
 * Copyright (c) 1996-2004 Borland Software Corp. All Rights Reserved.
 *
 * This SOURCE CODE FILE, which has been provided by Borland as part
 * of a Borland product for use ONLY by licensed users of the product,
 * includes CONFIDENTIAL and PROPRIETARY information of Borland.
 *
 * USE OF THIS SOFTWARE IS GOVERNED BY THE TERMS AND CONDITIONS
 * OF THE LICENSE STATEMENT AND LIMITED WARRANTY FURNISHED WITH
 * THE PRODUCT.
 *
 * IN PARTICULAR, YOU WILL INDEMNIFY AND HOLD BORLAND, ITS RELATED
 * COMPANIES AND ITS SUPPLIERS, HARMLESS FROM AND AGAINST ANY
 * CLAIMS OR LIABILITIES ARISING OUT OF THE USE, REPRODUCTION, OR
 * DISTRIBUTION OF YOUR PROGRAMS, INCLUDING ANY CLAIMS OR LIABILITIES
 * ARISING OUT OF OR RESULTING FROM THE USE, MODIFICATION, OR
 * DISTRIBUTION OF PROGRAMS OR FILES CREATED FROM, BASED ON, AND/OR
 * DERIVED FROM THIS SOURCE CODE FILE.
 */
//--------------------------------------------------------------------------------------------------
// Copyright (c) 1996 - 2004 Borland Software Corporation. All Rights Reserved.
//--------------------------------------------------------------------------------------------------
package com.borland.jbcl.model;

import java.awt.Component;

import com.borland.dx.dataset.Column;
import com.borland.dx.dataset.DataChangeEvent;
import com.borland.dx.dataset.DataChangeListener;
import com.borland.dx.dataset.DataSet;
import com.borland.jb.util.ExceptionHandler;

/**
 * VectorDataSetManager is the dataset data-aware adapter that allows vector
 * jbcl model-view components to talk to com.borland.jbcl.dataset components.
 */
public class VectorDataSetManager implements WritableVectorModel,
    VectorViewManager, DataChangeListener, ExceptionHandler {
  public VectorDataSetManager(DataSet dataSet, Column column) {
    this(dataSet, column, null);
  }
  
  public VectorDataSetManager(DataSet dataSet, Column column,
      Component component) {
    this.dataSet = dataSet;
    this.dataSetModel = new DataSetModel(dataSet, column, component);
  }
  
  // VectorModel Implementation
  
  public Object get(int row) {
    return dataSetModel.get(row);
  }
  
  public int find(Object data) {
    /*
     * Object coming in must be inspected (via instanceof) to figure out what it
     * is (ie variant, String, Integer, Long, Date, Time, TimeStamp, Boolean,
     * etc)
     */
    return -1;
  }
  
  public int getCount() {
    return dataSetModel.getRowCount();
  }
  
  public void addModelListener(VectorModelListener listener) {
    modelListeners.add(listener);
    if (modelListeners.getListenerCount() == 1)
      dataSet.addDataChangeListener(this);
  }
  
  public void removeModelListener(VectorModelListener listener) {
    modelListeners.remove(listener);
    if (modelListeners.getListenerCount() == 0)
      dataSet.removeDataChangeListener(this);
  }
  
  // WritableVectorModel Implementation
  
  public boolean canSet(int row, boolean startEdit) {
    return dataSetModel.canSet(row, startEdit);
  }
  
  public void set(int index, Object data) {
    dataSetModel.set(index, data);
    processModelEvent(VectorModelEvent.ITEM_CHANGED, index);
  }
  
  public void touched(int index) {
    processModelEvent(VectorModelEvent.ITEM_TOUCHED, index);
  }
  
  public boolean isVariableSize() {
    return true;
  }
  
  public void addItem(int aheadOf, Object data) {
    dataSetModel.addRow(aheadOf);
    dataSetModel.set(data);
  }
  
  public void addItem(Object data) {
    dataSetModel.addRow();
    dataSetModel.set(data);
  }
  
  public void remove(int index) {
    dataSetModel.removeRow(index);
  }
  
  public void removeAll() {
    // Do nothing. We really don't want this to happen with a DataSet.
  }
  
  public void enableModelEvents(boolean enable) {
    if (events != enable) {
      events = enable;
      if (enable)
        processModelEvent(VectorModelEvent.STRUCTURE_CHANGED, 0);
    }
  }
  
  // VectorViewManager implementation.
  
  public ItemPainter getPainter(int index, Object value, int state) {
    return dataSetModel.getPainter(index, value);
  }
  
  public ItemEditor getEditor(int index, Object value, int state) {
    return dataSetModel.getEditor();
  }
  
  // Event Translation Layer
  
  private void processModelEvent(int id, int row) {
    if (events && modelListeners.hasListeners())
      modelListeners.dispatch(new VectorModelEvent(this, id, row));
  }
  
  // (DataChangeEvent --> VectorModelEvent)
  
  public void dataChanged(DataChangeEvent e) {
    switch (e.getID()) {
    case DataChangeEvent.ROW_ADDED:
      processModelEvent(VectorModelEvent.ITEM_ADDED, e.getRowAffected());
      break;
    case DataChangeEvent.ROW_CANCELED:
    case DataChangeEvent.ROW_DELETED:
      processModelEvent(VectorModelEvent.ITEM_REMOVED, e.getRowAffected());
      break;
    case DataChangeEvent.ROW_CHANGED:
    case DataChangeEvent.ROW_CHANGE_POSTED:
      processModelEvent(VectorModelEvent.ITEM_CHANGED, e.getRowAffected());
      break;
    case DataChangeEvent.DATA_CHANGED:
      processModelEvent(ModelEvent.STRUCTURE_CHANGED, 0);
      break;
    }
  }
  
  public void postRow(DataChangeEvent e) throws Exception {
    // Handled directly by controls.
  }
  
  // ExceptionHandler Implementation
  
  public void handleException(Exception x) {
    dataSetModel.handleThisException(x);
  }
  
  private final DataSet dataSet;
  private final DataSetModel dataSetModel;
  private boolean events = true;
  private transient final com.borland.jb.util.EventMulticaster modelListeners = new com.borland.jb.util.EventMulticaster();
}
