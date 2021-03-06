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
//------------------------------------------------------------------------------
package com.borland.jbcl.model;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * The ItemPainter interface allows the JBCL model/view/item components to paint
 * individual items without knowledge of their data type.  This interface is used
 * to calculate individual item sizes, and to paint them in the hosting component.
 * The ItemPainter interface is used for all painting of items across all the JBCL
 * components, so a single implementation can be used in any JBCL component -
 * regardless of the components model type.
 */
public interface ItemPainter
{
  /**
   * This is the default state.
   */
  public static final int DEFAULT = 0x0000;

  /**
   * Set if this item is disabled.
   */
  public static final int DISABLED = 0x0001;

  /**
   * Set if this item has input focus.
   */
  public static final int FOCUSED = 0x0002;

  /**
   * Set if this item is selected or checked.
   */
  public static final int SELECTED = 0x0004;

  /**
   * Set if this item has an unknown selected state (overrides selected).
   */
  public static final int INDETERMINATE = 0x0008;

  /**
   * Set if this item contents are open (otherwise closed).
   */
  public static final int OPENED = 0x0010;

  /**
   * Set if this item's owning window is inactive / not focused.
   */
  public static final int INACTIVE = 0x0020;

  /**
   * Set if this item is in a rollover state (mouse over).
   */
  public static final int ROLLOVER = 0x0040;

  /**
   * Set if this item's containing component is not the current focus owner.
   */
  public static final int NOT_FOCUS_OWNER = 0x0080;

  static int[] states = new int[] {
    DEFAULT,
    DISABLED,
    FOCUSED,
    SELECTED,
    INDETERMINATE,
    OPENED,
    INACTIVE,
    ROLLOVER,
    NOT_FOCUS_OWNER
  };

  static String[] stateNames = new String[] {
    "DEFAULT",        
    "DISABLED",       
    "FOCUSED",        
    "SELECTED",       
    "INDETERMINATE",  
    "OPENED",         
    "INACTIVE",       
    "ROLLOVER",       
    "NOT_FOCUS_OWNER" 
  };

  /**
   * Returns the preferred size of the ItemPainter.<P>
   *
   * An ItemPainter implementation can use as much or as little information as it likes to determine
   * a 'preferred' size for an item.  For example, the com.borland.jbcl.view.TextItemPainter uses the
   * text from the data object (toString()), the font from the ItemPaintSite, and the Graphics object
   * to determine the width and height of the item.  It ignores the state information, and everything
   * else in the ItemPaintSite.
   *
   * @param data The data object to use for size calculation.
   * @param graphics The Graphics object to use for size calculation.
   * @param state The current state of the object.
   * @param site The ItemPaintSite with information about fonts, margins, etc.
   * @return The calculated Dimension object representing the preferred size of this ItemPainter.
   */
  public Dimension getPreferredSize(Object data, Graphics graphics, int state, ItemPaintSite site);

  /**
   * Paints the data Object within the Rectangle rect, using passed Graphics
   * and state information.<P>
   *
   * An ItemPainter implementation can use as much or as little information as it likes to paint an
   * item.  For example, the com.borland.jbcl.view.FocusableItemPainter ignores the data object and only
   * draws a dotted rectangle if the state bitmask includes the FOCUSED bit.  Another example is the
   * com.borland.jbcl.view.TextItemPainter, which uses nearly all of passed information to draw the
   * appropriate text in the passed rectangle.
   *
   * @param data The data object to paint.
   * @param graphics The Graphics object to paint to.
   * @param rect The Rectangle extents to paint in.
   * @param state The current state information for the data object.
   * @param site The ItemPaintSite with information about fonts, margins, etc.
   */
  public void paint(Object data, Graphics graphics, Rectangle rect, int state, ItemPaintSite site);
}
