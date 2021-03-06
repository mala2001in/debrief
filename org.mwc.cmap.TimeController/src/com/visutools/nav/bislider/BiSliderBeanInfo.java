/*
 *    Debrief - the Open Source Maritime Analysis Application
 *    http://debrief.info
 *
 *    (C) 2000-2014, PlanetMayo Ltd
 *
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the Eclipse Public License v1.0
 *    (http://www.eclipse.org/legal/epl-v10.html)
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 */
package com.visutools.nav.bislider;

/**
 * The bean info entry point.
 * <br><br>
 * <table border=1 width = "90%">
 *   <tr>
 *     <td>
 *       Copyright 1997-2005 Frederic Vernier. All Rights Reserved.<br>
 *       <br>
 *       Permission to use, copy, modify and distribute this software and its documentation for educational, research and
 *       non-profit purposes, without fee, and without a written agreement is hereby granted, provided that the above copyright
 *       notice and the following three paragraphs appear in all copies.<br>
 *       <br>
 *       To request Permission to incorporate this software into commercial products contact
 *       Frederic Vernier, 19 butte aux cailles street, Paris, 75013, France. Tel: (+33) 871 747 387.
 *       eMail: Frederic.Vernier@laposte.net / Web site: http://vernier.frederic.free.fr
 *       <br>
 *       IN NO EVENT SHALL FREDERIC VERNIER BE LIABLE TO ANY PARTY FOR DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL
 *       DAMAGES, INCLUDING LOST PROFITS, ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF FREDERIC
 *       VERNIER HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.<br>
 *       <br>
 *       FREDERIC VERNIER SPECIFICALLY DISCLAIMS ANY WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 *       MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE PROVIDED HERE UNDER IS ON AN "AS IS" BASIS, AND
 *       FREDERIC VERNIER HAS NO OBLIGATIONS TO PROVIDE MAINTENANCE, SUPPORT, UPDATES, ENHANCEMENTS, OR MODIFICATIONS.<br>
 *     </td>
 *   </tr>
 * </table>
 * <br>
 * <b>Project related :</b>  FiCell, FieldExplorer<br>
 * <br>
 * <b>Dates:</b><br>
 *   <li>Creation    : XXX<br>
 *   <li>Format      : 15/02/2004<br>
 *   <li>Last Modif  : 15/02/2004<br>
 *<br>
 * <b>Bugs:</b><br>
 *<br>
 * <b>To Do:</b><br>
 *<br>
 * @author Frederic Vernier, Frederic.Vernier@laposte.net
 * @version 1.4.1
 **/

public class BiSliderBeanInfo extends java.beans.SimpleBeanInfo {
  // Generated BeanInfo just gives the bean its icons.
  // Small icon is in BiSlider.gif
  // Large icon is in BiSliderL.gif
  // It is expected that the contents of the icon files will be changed to suit your bean.

  public java.awt.Image getIcon(int iconKind) {
    java.awt.Image icon = null;
    switch (iconKind)
    {
      case ICON_COLOR_16x16:
        // The "/" is very important. It doesn't mean the image must
        // be found at the root of the HD but at the root of the
        // classpath entries (the jar of the bean is a kind of root then !)
        icon = loadImage("/images/BiSlider.png");
        break;

      case ICON_COLOR_32x32:
        // The "/" is very important. It doesn't mean the image must
        // be found at the root of the HD but at the root of the
        // classpath entries (the jar of the bean is a kind of root then !)
        icon = loadImage("/images/BiSliderL.png");
        break;

      default:
        icon = loadImage("/images/BiSliderVL.png");
        break;
    }
    return icon;
  }
}

/* BiSliderBeanInfo.java */
