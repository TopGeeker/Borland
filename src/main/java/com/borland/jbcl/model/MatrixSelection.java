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

/**
 * The MatrixSelection interface provides read access
 * to a selection pool for a MatrixModel model.
 * <P>
 * This is typically used to pass a selection set.
 */
public interface MatrixSelection
{
  /**
   * Returns true if location is in selection pool.
   */
  boolean contains(MatrixLocation location);
  boolean contains(int row, int column);

  /**
   * Returns count of locations in the selection pool.
   */
  int getCount();

  /**
   * Returns all locations in the selection pool.
   */
  MatrixLocation[] getAll();

  /**
   * Adds a selection event listener to the selection pool.
   */
  void addSelectionListener(MatrixSelectionListener listener);

  /**
   * Removes a selection event listener from the selection pool.
   */
  void removeSelectionListener(MatrixSelectionListener listener);
}
