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
package Debrief.ReaderWriter.PCArgos;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import Debrief.GUI.Frames.Application;
import MWC.GUI.Layers;
import MWC.GUI.Properties.PropertiesPanel;
import MWC.GenericData.WorldLocation;
import MWC.Utilities.TextFormatting.GMTDateFormat;

public final class SwingImportRangeData extends ImportRangeDataPanel
{
  ///////////////////////////////////
  // member variables
  //////////////////////////////////

  private JPanel thisPanel;



  // Variables declaration - do not modify
  private javax.swing.JPanel ButtonPanel;
  private javax.swing.JButton importBtn;
  private javax.swing.JButton closeBtn;
  private javax.swing.JPanel PropertiesList;
  private javax.swing.JPanel FilenamePanel;
  private javax.swing.JLabel jLabel3;
  private javax.swing.JLabel FilenameLabel;
  private javax.swing.JButton selectFileBtn;
  private javax.swing.JPanel FrequencyPanel;
  private javax.swing.JLabel FrequencyLabel;
  private TimeFrequencyCombo FrequencyCombo;
  private javax.swing.JPanel OriginPanel;
  private javax.swing.JLabel jLabel7;
  private javax.swing.JLabel OriginLabel;
  private javax.swing.JButton selectOriginBtn;
  private javax.swing.JPanel DTGPanel;
  private javax.swing.JLabel jLabel9;
  private javax.swing.JTextField _theDate;
  // End of variables declaration


  /** the warning/introduction panel
   */
  private JTextArea _theWarning;

  /** the format to use for the data field
   */
  private final DateFormat _dateF = new GMTDateFormat("yyyy/MM/dd");


  ///////////////////////////////////
  // constructor
  //////////////////////////////////
  public SwingImportRangeData(final Layers theData,
                              final String lastDirectory,
                              final PropertiesPanel thePanel)
  {
    super(theData, lastDirectory, thePanel);
  }



  ///////////////////////////////////
  // member functions
  //////////////////////////////////
  protected final void initForm()
  {
    thisPanel = new JPanel();
    thisPanel.setName("Import Range Data");
    thisPanel.setLayout (new java.awt.BorderLayout ());

    _thePanel.add(thisPanel);

    _theWarning = new JTextArea();
    _theWarning.setEditable(false);
    String theMessage = "Warning, data imported through this panel has";
    theMessage += " speed and course calculated as over the ground.";
    theMessage += System.getProperties().getProperty("line.separator");
    theMessage += "Debrief recognises PC Argos (RAO) and PMRF (PRN) files.";
    theMessage += System.getProperties().getProperty("line.separator");
    theMessage += "See Debrief Help File for file format and details.";

    _theWarning.setText(theMessage);
    _theWarning.setLineWrap(true);
    _theWarning.setBorder(BorderFactory.createLoweredBevelBorder());
    _theWarning.setWrapStyleWord(true);
    thisPanel.add("North", _theWarning);
    ////////////////////////////////////////////
    // auto generated stuff
    ////////////////////////////////////////////

    ButtonPanel = new javax.swing.JPanel ();
    importBtn = new javax.swing.JButton ();
    closeBtn = new javax.swing.JButton ();
    PropertiesList = new javax.swing.JPanel ();
    FilenamePanel = new javax.swing.JPanel ();
    jLabel3 = new javax.swing.JLabel ();
    FilenameLabel = new javax.swing.JLabel ();
    selectFileBtn = new javax.swing.JButton ();
    FrequencyPanel = new javax.swing.JPanel ();
    FrequencyLabel = new javax.swing.JLabel ();
    FrequencyCombo = new TimeFrequencyCombo();
    OriginPanel = new javax.swing.JPanel ();
    jLabel7 = new javax.swing.JLabel ();
    OriginLabel = new javax.swing.JLabel ();
    selectOriginBtn = new javax.swing.JButton ();
    DTGPanel = new javax.swing.JPanel ();
    jLabel9 = new javax.swing.JLabel ();
    _theDate = new javax.swing.JTextField("2001/01/30");

    /////////////////////////////////////////////////
    // button panel first
    //////////////////////////////////////////////////

    importBtn.setText ("Import");
    ButtonPanel.add (importBtn);
    closeBtn.setText ("Close");
    ButtonPanel.add (closeBtn);
    thisPanel.add (ButtonPanel, java.awt.BorderLayout.SOUTH);
    closeBtn.addActionListener(new ActionListener(){
      public void actionPerformed(final ActionEvent e)
      {
        doClose();
      }
      });
    importBtn.addActionListener(new ActionListener(){
      public void actionPerformed(final ActionEvent e)
      {
        doImport();
      }
      });

    ///////////////////////////////////////////////////
    // now the properties panel
    ///////////////////////////////////////////////////

    PropertiesList.setLayout (new java.awt.GridLayout (0, 1));


    jLabel3.setText ("Filename:");
    FilenamePanel.add (jLabel3);
    FilenameLabel.setText ("    blank    ");
    FilenamePanel.add (FilenameLabel);
    selectFileBtn.setPreferredSize (new java.awt.Dimension(33, 27));
    selectFileBtn.setToolTipText ("Select file");
    selectFileBtn.setHorizontalTextPosition (javax.swing.SwingConstants.CENTER);
    selectFileBtn.setText ("...");
    selectFileBtn.addActionListener(new ActionListener(){
      public void actionPerformed(final ActionEvent e)
      {
        doEditFilename();
      }
      });
    FilenamePanel.add (selectFileBtn);
    PropertiesList.add (FilenamePanel);


    FrequencyLabel.setText ("Frequency:");
    FrequencyPanel.add (FrequencyLabel);
    FrequencyCombo.setPreferredSize (new java.awt.Dimension(100, 25));
    FrequencyPanel.add (FrequencyCombo);
    PropertiesList.add (FrequencyPanel);

    jLabel7.setText ("Origin:");
    OriginPanel.add (jLabel7);
    OriginLabel.setText ("    blank    ");
    OriginPanel.add (OriginLabel);
    selectOriginBtn.setPreferredSize (new java.awt.Dimension(33, 27));
    selectOriginBtn.setToolTipText ("Select file");
    selectOriginBtn.setHorizontalTextPosition (javax.swing.SwingConstants.CENTER);
    selectOriginBtn.setText ("...");
    selectOriginBtn.addActionListener(new ActionListener(){
      public void actionPerformed(final ActionEvent e)
      {
        editOrigin();
      }
      });
    OriginPanel.add (selectOriginBtn);
    PropertiesList.add (OriginPanel);


    //////////////////////////////////////////////////////
    // DTG
    /////////////////////////////////////////////////////

    jLabel9.setText ("DTG (yyyy/mm/dd):");
    _theDate.setText("2001/01/30");
    _theDate.addFocusListener(new FocusAdapter(){
      public void focusLost(final FocusEvent e)
      {
        checkDTG();
      }
      });
    DTGPanel.add(jLabel9);
    DTGPanel.add (_theDate);
    PropertiesList.add (DTGPanel);

    final JPanel jp = new JPanel();
    jp.setLayout(new FlowLayout());
    jp.add(PropertiesList);

    thisPanel.add (jp, java.awt.BorderLayout.CENTER);
  }

  void editOrigin()
  {
      final WorldLocation val = MWC.GUI.Properties.Swing.SwingWorldLocationEditorFrame.doEdit(_theOrigin);
      if(val != null)
      {
        ImportRangeDataPanel._theOrigin = val;
        refreshForm();
      }
  }

  void doEditFilename()
  {
    // create the file open dialog
    final JFileChooser jf = new JFileChooser();

    // cancel multiple selections
    jf.setMultiSelectionEnabled(false);

    // set the start directory
    if(_lastDirectory != null)
    {
      jf.setCurrentDirectory(new File(_lastDirectory));
    }
    else
    {

      String val = "";

      // see if we have an old directory to retrieve
      val = Application.getThisProperty("RANGE_Directory");

      // give it a default value, if we have to
      if(val == null)
        val = "";

      // try to get the import directory
      jf.setCurrentDirectory(new File(val));
    }

    // open the dialog
    final int state = jf.showOpenDialog(null);

    // do we have a valid file?
    if(state == JFileChooser.APPROVE_OPTION)
    {
      // retrieve the filename
      final File theFile = jf.getSelectedFile();
      _theFilename = theFile.getPath();

      // retrieve the directory name
      _lastDirectory = theFile.getParent();

      // trigger a refresh
      refreshForm();
    }
    else if(state == JFileChooser.CANCEL_OPTION)
    {
      _theFilename = null;
    }

  }

  final void doClose()
  {
    _thePanel.remove(thisPanel);

    super.doClose();
  }

  protected synchronized final void refreshForm()
  {
    // set the location
    OriginLabel.setText(MWC.Utilities.TextFormatting.BriefFormatLocation.toString(ImportRangeDataPanel._theOrigin));

    // set the filename
    FilenameLabel.setText(super._theFilename);

    // special case - in constructor we may get called before the _dateF
    // has been initialised
    if(_dateF != null)
    {
    	// set the DTG
    	_theDate.setText(_dateF.format(new Date(ImportRangeDataPanel._theDTG)));
    }

  }

  synchronized void checkDTG()
  {
    try{
      _theDTG = _dateF.parse(_theDate.getText()).getTime();
    }
    catch(final ParseException e)
    {
      MWC.GUI.Dialogs.DialogFactory.showMessage("Date Format Error", "Sorry, date incorrectly formatted (2001/1/21)");
    }
  }


  final void doImport()
  {
    // ok, let's commit to the current settings, and retrieve the
    // current frequency
    _theFreq = (long)(FrequencyCombo.getValue() * 1000);

    // let the parent do the import
    super.doImport();

  }

  ////////////////////////////////////////////////////////////////////////
  // nested class custom editor for time frequencies
  //////////////////////////////////////////////////////////////////////////
  @SuppressWarnings("rawtypes")
	static final class TimeFrequencyCombo extends JComboBox
  {

    /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		long _myFreq;

    private final String[] stringTags =
    {
                       "1 Sec",
                       "5 Secs",
                       "10 Secs",
                       "15 Secs",
                       "30 Secs",
                       "1 Min",
                       "5 Mins",
                       "10 Mins",
                       "15 Mins",
                       "30 Mins",
                       "60 Mins",
                       "None"};

    private final long[] freqs =
    {
            1,
            5,
            10,
            15,
            30,
            60,
      5   * 60,
      10  * 60,
      15  * 60,
      30  * 60,
      60  * 60,
      0};


    @SuppressWarnings("unchecked")
		public TimeFrequencyCombo()
    {
      super();
      for(int i=0;i<stringTags.length;i++)
      {
        this.addItem(stringTags[i]);
      }
      this.addActionListener(new ActionListener(){
        public void actionPerformed(final ActionEvent e)
        {
          newSelection();
        }
        });
    }

    final void newSelection()
    {
      // update the local copy
      final String val = (String)this.getSelectedItem();

      // find out what value this string is
      for(int i=0;i<stringTags.length;i++)
      {
        final String thisS = stringTags[i];
        if(thisS.equals(val))
        {
          _myFreq = freqs[i];
        }
      }
    }

    public final double getValue()
    {
      return _myFreq;
    }

    public final void setValue(final long val)
    {
      for(int i=0;i<freqs.length;i++)
      {
        final long thisS = freqs[i];
        if(thisS == val)
        {
          final String thisFreq = stringTags[i];
          setSelectedItem(thisFreq);
        }
      }
    }
  }


}
