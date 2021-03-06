//--------------------------------------------------------------------------------------------------
// $Header$
// Copyright (c) 1996-2002 Borland Software Corporation. All Rights Reserved.
//--------------------------------------------------------------------------------------------------

package com.borland.dx.sql.dataset;

import java.util.EventListener;

/**
 * This interface is used for notification before and after closing a database
 * connection or changing the attributes of the JDBC connection.
 */
public interface ConnectionUpdateListener extends EventListener {
  /**
  * The event that gets fired when a database connection has changed.
   */
  public void connectionChanged(ConnectionUpdateEvent event);
  /**
  * The event that gets fired when a database connection has closed.
   */
  public void connectionClosed(ConnectionUpdateEvent event);
  /**
   *The event that gets fired when a database connection is about to change.
   *  Throwing an Exception will block the connection change
   */
  public void canChangeConnection(ConnectionUpdateEvent event) throws Exception;
  /**
  * The event that gets fired when a database connection is being opened.
   */
  public void connectionOpening(ConnectionUpdateEvent event);
}
