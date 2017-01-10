
package MVGPC;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;

import java.io.FileWriter;
import java.io.PrintWriter;

import java.text.DecimalFormat;

import java.util.Locale;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;


// Referenced classes of package MVGPC:
//            AListener, PreprocessAction, MainClass, Preprocess, 
//            GeneStat, Parameters, Statistics

public class MainFrame extends JFrame
    implements Runnable
{

    public MainFrame()
    {
        FIXEDSPLIT = false;
        UCIML = false;
        EXAMPLE = false;
        examplefiles = (new String[] {
            "WCBreast.txt", "BreastCancer.txt", "Monk.txt"
        });
        JlabelClasses = new JLabel();
        JlabelFileName = new JLabel();
        JlabelAttribute = new JLabel();
        JlabelSample = new JLabel();
        JlabelTrainSamples = new JLabel();
        JlabelPopSize = new JLabel();
        JlabelRun = new JLabel();
        JTextValidFile = new JTextField();
        JTextSample = new JTextField();
        JTextAttribute = new JTextField();
        JTextClasses = new JTextField();
        jLabelHeadline = new JLabel();
        jtxtTrainsetInfo = new JTextField();
        JLabelTrainSize = new JLabel();
        JTextTrainSize = new JTextField();
        JTextTestSize = new JTextField();
        JTextPopSize = new JTextField();
        JlabelMaxGen = new JLabel();
        JlabelFunctions = new JLabel();
        JlabelOperators = new JLabel();
        JlabelDepth = new JLabel();
        JlabelCRun = new JLabel();
        JlabelMember = new JLabel();
        JTextMaxGen = new JTextField();
        JTextRun = new JTextField();
        JTextMember = new JTextField();
        JTextFunctions = new JTextField();
        JTextOperators = new JTextField();
        JTextDepth = new JTextField();
        JButtonRun = new JButton();
        JButtonStop = new JButton();
        JButtonReset = new JButton();
        JLabelGenInfo = new JLabel();
        JLabelRuleInfo = new JLabel();
        JlabelCBestRule = new JLabel();
        JTextAreaRule = new JTextArea();
        jScrollPaneSolution = new JScrollPane();
        jTabbedPaneMVGPC = new JTabbedPane();
        jPanelMain = new JPanel();
        jPanelViewResults = new JPanel();
        buttonGroupSplit = new ButtonGroup();
        jRButtonFixed = new JRadioButton();
        jRButtonRandom = new JRadioButton();
        buttonGroupFileType = new ButtonGroup();
        jRButtonMicroarray = new JRadioButton();
        jRButtonUCIML = new JRadioButton();
        jLabelFileType = new JLabel();
        jLabelSplitType = new JLabel();
        jLabelTextsize = new JLabel();
        jScrollPaneViewResults = new JScrollPane();
        jPanelGene = new JPanel();
        jProgressBarTask = new JProgressBar();
        jScrollPaneGene = new JScrollPane();
        jlblAttrTypes = new JLabel();
        jtxtAttrTypes = new JTextField();
        jlblAttrInfo = new JLabel();
        jlblTableTitle = new JLabel();
        jblGTableTitle = new JLabel();
        jblGTableMore = new JLabel();
        jlblTrainExamp = new JLabel();
        jComboBoxDataFile = new JComboBox(examplefiles);
        jLabelValidFile = new JLabel();
        jLabelMStat = new JLabel();
        jLabelPStat = new JLabel();
        jCancer = new JLabel();
        jPanelAbout = new JPanel();
        jLabelmvgpcinfo = new JLabel();
        jButtonExit = new JButton();
        jPanelPreProcess = new JPanel();
        jCheckBoxNorm = new JCheckBox();
        buttonGroupNorm = new ButtonGroup();
        jRadioButtonLN = new JRadioButton();
        jRadioButtonLog10 = new JRadioButton();
        jRadioButtonLinear = new JRadioButton();
        jTextFieldLRange = new JTextField();
        jLabelLRange = new JLabel();
        jCheckBoxPrepro = new JCheckBox();
        jCheckBoxMGP = new JCheckBox();
        jLabellth = new JLabel();
        jTextFieldLTH = new JTextField();
        jLabelhth = new JLabel();
        jTextFieldHTH = new JTextField();
        jLabelDiff = new JLabel();
        jTextFieldDiff = new JTextField();
        jLabelFold = new JLabel();
        jTextFieldFold = new JTextField();
        jButtonProcess = new JButton();
        jButtonPReset = new JButton();
        jLabelDfile = new JLabel();
        jTextFieldGetDfile = new JTextField();
        jLabelOutPFile = new JLabel();
        jTextFieldOutPFile = new JTextField();
        jLabelStatus = new JLabel();
       
        jLabelDefOutfile = new JLabel();
        jCheckBoxFcol = new JCheckBox();
        jCheckBoxFrow = new JCheckBox();
        jButtonPExit = new JButton();
        jLabelExcludeGene = new JLabel();
        
        enableEvents(64L);
        try
        {
        jbInit();
        }
        catch(Exception e)
        {
        e.printStackTrace();
        }
    }

    void DisplayMessage()
    {
        String displaytext = "<HTML><b>MVGPC is a powerful tool for data preprocessing, data classification, and identification of important features (genes)</b>.It can handle:<ul><li>Microarray gene expression data and UCI machine learning databases (with no missing values);</li><li>Numerical, nominal attributes (in numeric format) and binary attributes;</li><li>Arithmetic and/or logical functions;</li><li>Fixed and random split of data into training and test subsets;</li><li>Training and validation data stored in two separate files; and</li><li> More... Try it Now! Three examples are available!</li></ul></HTML>";
        jLabelmvgpcinfo.setText(displaytext);
    }

    void ExampleValues()
    {
        String filename = jComboBoxDataFile.getEditor().getItem().toString();
        int sample = 0;
        int attribute = 0;
        int nclasses = 2;
        String trainsetinfo = null;
        String funcset = "+:-:*:/:SQR:SQRT";
        String attrinfo = "N";
        String traininfolabel = "Number of different training samples:";
        String traininfoexamp = "Ex:179:106:56";
        UCIML = false;
        FIXEDSPLIT = false;
        jtxtTrainsetInfo.setEnabled(true);
        JTextValidFile.setEnabled(true);
        JTextValidFile.setText(null);
        EXAMPLE = false;
        if(filename.compareTo("WCBreast.txt") == 0)
        {
            UCIML = true;
            FIXEDSPLIT = false;
            sample = 569;
            attribute = 30;
            nclasses = 2;
            attrinfo = "N1:30";
            trainsetinfo = "179:106";
            funcset = "+:-:*:/:SQR:SQRT:AND:OR:NOT:=:<>:>:<:>=:<=";
            jtxtTrainsetInfo.setText(trainsetinfo);
            JTextValidFile.setEnabled(false);
            EXAMPLE = true;
        } else
        if(filename.compareTo("BreastCancer.txt") == 0)
        {
            UCIML = false;
            FIXEDSPLIT = true;
            sample = 22;
            attribute = 3226;
            nclasses = 3;
            attrinfo = "N1:3226";
            trainsetinfo = "6:6:5";
            traininfolabel = "Filename containing training indexes:";
            traininfoexamp = "Ex:trainset.txt";
            funcset = "+:-:*:/:SQR:SQRT";
            jtxtTrainsetInfo.setText(trainsetinfo);
            JTextValidFile.setEnabled(false);
            EXAMPLE = true;
        } else
        if(filename.compareTo("Monk.txt") == 0)
        {
            UCIML = true;
            FIXEDSPLIT = true;
            sample = 556;
            attribute = 6;
            nclasses = 2;
            attrinfo = "L1:6:4";
            funcset = "AND:OR:NOT:=:<>:>:<:>=:<=";
            jtxtTrainsetInfo.setText(null);
            jtxtTrainsetInfo.setEnabled(false);
            JTextValidFile.setText("C:\\DataFile\\MonkValid.txt");
            EXAMPLE = true;
        }
        if(UCIML)
            jRButtonUCIML.setSelected(true);
        else
            jRButtonMicroarray.setSelected(true);
        if(FIXEDSPLIT)
            jRButtonFixed.setSelected(true);
        else
            jRButtonRandom.setSelected(true);
        JTextSample.setText(Integer.toString(sample));
        JTextAttribute.setText(Integer.toString(attribute));
        JTextClasses.setText(Integer.toString(nclasses));
        jtxtAttrTypes.setText(attrinfo);
        JlabelTrainSamples.setText(traininfolabel);
        jlblTrainExamp.setText(traininfoexamp);
        JTextPopSize.setText("1000");
        JTextMaxGen.setText("50");
        JTextRun.setText("20");
        JTextMember.setText("3");
        JTextFunctions.setText(funcset);
        
        
    }

    void ExecuteMVGPC()
    {
        int run = 0;
        try
        {
            jProgressBarTask.setValue(0);
            String DataFile;
            if(EXAMPLE)
                DataFile = "C:\\DataFile\\" + jComboBoxDataFile.getEditor().getItem().toString();
            else
                DataFile = jComboBoxDataFile.getEditor().getItem().toString();
            String ValidationFile = JTextValidFile.getText();
            int SAMPLE = Integer.parseInt(JTextSample.getText());
            int ATTRIBUTE = Integer.parseInt(JTextAttribute.getText());
            int NCLASS = Integer.parseInt(JTextClasses.getText());
            int POPSIZE = Integer.parseInt(JTextPopSize.getText());
            int MAXGEN = Integer.parseInt(JTextMaxGen.getText());
            int MAXRUN = Integer.parseInt(JTextRun.getText());
            int MEMBERS = Integer.parseInt(JTextMember.getText());
            int TRAINSIZE = 0;
            int TESTSIZE = 0;
            int trainsamples[] = new int[NCLASS];
            JTable jTableCorrect = new JTable((MEMBERS + 2) * MAXRUN, 5);
            jTableCorrect.setBorder(BorderFactory.createEtchedBorder());
            jScrollPaneViewResults.getViewport().add(jTableCorrect, null);
            WriteTableHead(jTableCorrect, "Rule/Set:Training(#):Test(#):Training(%):Test(%)");
            JTable jTableGeneFreq = new JTable(Math.min(25, ATTRIBUTE), 3);
            jScrollPaneGene.getViewport().add(jTableGeneFreq, null);
            if(ATTRIBUTE < 25)
                jblGTableMore.setText(" ");
            else
                jblGTableMore.setText("Frequencies of other genes (if any) are available in GeneFreq...file.");
            WriteTableHead(jTableGeneFreq, "Rank:Attribute/Gene Id:Frequency");
            String funcset = JTextFunctions.getText().toUpperCase();
            // Start of operator implementation
            String OperatorSet = JTextOperators.getText().toUpperCase();
            int OperatorDepth =Integer.parseInt(JTextDepth.getText());
            if(jCheckBoxMGP.isSelected()) {
                OperatorDepth=0;
                OperatorSet = "";
                Boolean MetaGP = true;
            }
            //End operator implementation
            String attrTypes = jtxtAttrTypes.getText().toUpperCase();
            if(attrTypes.compareTo("N1:30") == 0)
                attrTypes = "N1:" + ATTRIBUTE;
            MainClass mvgpcmain = new MainClass(POPSIZE, MAXGEN, ATTRIBUTE, attrTypes, SAMPLE, funcset,OperatorDepth,OperatorSet);
            if(ValidationFile != null && ValidationFile.length() > 0 && ValidationFile.trim().length() > 0)
            {
                mvgpcmain.ReadData(DataFile, ValidationFile, UCIML, NCLASS);
                FIXEDSPLIT = true;
                TRAINSIZE = mvgpcmain.TRAINSIZE;
                TESTSIZE = mvgpcmain.TESTSIZE;
            } else
            {
                if(UCIML)
                    mvgpcmain.ReadUCIMLData(DataFile, NCLASS);
                else
                    mvgpcmain.ReadMicroarrayData(DataFile, NCLASS);
                if(jtxtTrainsetInfo.getText().indexOf(":") > -1)
                {
                    StringTokenizer traindist = new StringTokenizer(jtxtTrainsetInfo.getText(), ":");
                    int c = traindist.countTokens();
                    for(int k = 0; k < c; k++)
                    {
                        trainsamples[k] = Integer.parseInt(traindist.nextToken());
                        TRAINSIZE += trainsamples[k];
                    }

                    FIXEDSPLIT = false;
                } else
                {
                    FIXEDSPLIT = true;
                    TRAINSIZE = mvgpcmain.SplitDataFixed(jtxtTrainsetInfo.getText());
                }
                TESTSIZE = SAMPLE - TRAINSIZE;
            }
            JTextTrainSize.setText(String.valueOf(TRAINSIZE));
            JTextTestSize.setText(String.valueOf(TESTSIZE));
            String DataFileName = Parameters.getFileName(DataFile);
            String OutputFile = "Rules" + DataFileName;
            PrintWriter ResultFileRule = new PrintWriter(new FileWriter(OutputFile));
            OutputFile = "Accuracy" + DataFileName;
            PrintWriter ResultFileStat = new PrintWriter(new FileWriter(OutputFile));
            OutputFile = "GeneFreq" + DataFileName;
            PrintWriter ResultFileGene = new PrintWriter(new FileWriter(OutputFile));
            ResultFileRule.println("Cancer File=" + DataFile);
            ResultFileRule.println("Population=" + POPSIZE + "\tIndividual Length(Max)=" + Parameters.maxSize);
            ResultFileRule.println("Genes=" + ATTRIBUTE + "\tSamples=" + SAMPLE + "\tTraining Size=" + TRAINSIZE + "\tTest Size=" + TESTSIZE);
            mvgpcmain.RuleInterpret(ResultFileRule, NCLASS);
            ResultFileRule.flush();
            ResultFileStat.println("Cancer File=" + DataFile);
            ResultFileStat.println("Number of rules per class=" + MEMBERS);
            ResultFileStat.println("Set/Rule#\t#Training(" + TRAINSIZE + ")\t#Test(" + TESTSIZE + ")\t%Training\t%Test");
            ResultFileStat.flush();
            ResultFileGene.println("Rank\tGeneId\tFrequency");
            ResultFileGene.flush();
            int MaxPBarValue;
            if(NCLASS == 2)
                MaxPBarValue = MAXRUN * MEMBERS;
            else
                MaxPBarValue = MAXRUN * MEMBERS * NCLASS;
            mvgpcmain.GUIOutput(JTextAreaRule, JLabelRuleInfo, JLabelGenInfo, jTableCorrect, jProgressBarTask, MaxPBarValue);
            Statistics.initStat(MEMBERS * MAXRUN, MAXRUN);
            jLabelMStat.setText(" ");
            jLabelPStat.setText(" ");
            jCancer.setText(" ");
            for(run = 1; run <= MAXRUN && Parameters.RunningMode; run++)
            {
                long beforerun = System.currentTimeMillis();
                if(!FIXEDSPLIT)
                    mvgpcmain.SplitDataRandom(trainsamples);
                ResultFileRule.println("*******************Run:" + run + "************************");
                ResultFileStat.println("*******************Run:" + run + "************************");
                JlabelCRun.setText("Current Trial#:" + run);
                mvgpcmain.MVGPC(run, MEMBERS, ResultFileRule, ResultFileStat);
                long afterrun = System.currentTimeMillis();
                double etime = (double)(afterrun - beforerun) / 1000D;
                ResultFileRule.println("Total Execution Time=" + etime + " sec");
            }

            mvgpcmain.WriteGeneFreq(ResultFileGene);
            ShowGeneFreq(jTableGeneFreq);
            if(run > 1 && Parameters.RunningMode)
            {
                DecimalFormat dformat = new DecimalFormat("0.00");
                String minfo = "MVGPC: Training accuracy=";
                double trainstat[] = Statistics.getMVGPCTrainStat();
                double teststat[] = Statistics.getMVGPCTestStat();
                minfo = minfo + dformat.format(trainstat[0]) + "\261" + dformat.format(trainstat[1]) + "; Test accuracy=" + dformat.format(teststat[0]) + "\261" + dformat.format(teststat[1]);
                jLabelMStat.setText(minfo);
                ResultFileStat.println(minfo);
                minfo = "SR/SSR: Training accuracy=";
                trainstat = Statistics.getPTRTrainStat();
                teststat = Statistics.getPTRTestStat();
                minfo = minfo + dformat.format(trainstat[0]) + "\261" + dformat.format(trainstat[1]) + "; Test accuracy=" + dformat.format(teststat[0]) + "\261" + dformat.format(teststat[1]);
                jLabelPStat.setText(minfo);
                if(DataFileName.compareTo("Monk.txt")==0)
                {
                jCancer.setText("CANCER FILE");
                }
                else if((DataFileName.compareTo("BreastCancer.txt")==0) || (DataFileName.compareTo("WCBreast.txt")==0))
                {
                    jCancer.setText("BREAST CANCER");
                }
                else
                jCancer.setText("NORMAL FILE");
                ResultFileStat.println(minfo);
            }
            ResultFileRule.close();
            ResultFileStat.close();
            ResultFileGene.close();
            if(Parameters.RunningMode)
            {
                stop();
                exitEGPCRun();
            }
        }
        catch(Exception ex)
        {
            JTextAreaRule.setForeground(Color.RED);
            JTextAreaRule.setText(ex.toString());
            stop();
            exitEGPCRun();
        }
    }

    void PerformAction(ActionEvent e)
    {
        String option = e.getActionCommand();
        if(option.compareTo(jRButtonMicroarray.getText()) == 0)
            UCIML = false;
        else
        if(option.compareTo(jRButtonUCIML.getText()) == 0)
            UCIML = true;
        else
        if(option.compareTo(jRButtonRandom.getText()) == 0)
        {
            FIXEDSPLIT = false;
            JlabelTrainSamples.setText("Number of different training samples:");
            jlblTrainExamp.setText("Ex:179:106:56");
            jtxtTrainsetInfo.setText("");
        } else
        if(option.compareTo(jRButtonFixed.getText()) == 0)
        {
            if(JTextValidFile.getText() != null && JTextValidFile.getText().length() > 0 && JTextValidFile.getText().trim().length() > 0)
            {
                jtxtTrainsetInfo.setEnabled(false);
            } else
            {
                JlabelTrainSamples.setText("Filename containing training indexes:");
                jlblTrainExamp.setText("Ex:trainset.txt");
                jtxtTrainsetInfo.setText("");
            }
            FIXEDSPLIT = true;
        } else
        if(option.compareTo(JButtonRun.getText()) == 0)
        {
            Parameters.RunningMode = true;
            JTextAreaRule.setForeground(Color.BLUE);
            entryEGPCRun();
            start();
        } else
        if(option.compareTo(JButtonStop.getText()) == 0)
        {
            Parameters.RunningMode = false;
            exitEGPCRun();
            stop();
        } else
        if(option.compareTo(JButtonReset.getText()) == 0)
        {
            Parameters.RunningMode = false;
            ResetValues();
        } else
        if(option.compareTo(jComboBoxDataFile.getActionCommand()) == 0)
            ExampleValues();
        else
        if(option.compareTo(jButtonExit.getActionCommand()) == 0)
        {
            stop();
            System.exit(0);
        }
    }

    void ResetValues()
    {
        JlabelDepth.setEnabled(false);
        JlabelOperators.setEnabled(false);
        JTextOperators.setEnabled(false);
        JTextDepth.setEnabled(false);
        jProgressBarTask.setValue(0);
        jRButtonMicroarray.setSelected(true);
        UCIML = false;
        jRButtonRandom.setSelected(true);
        FIXEDSPLIT = false;
        JTextValidFile.setText("");
        jComboBoxDataFile.setSelectedIndex(-1);
        JTextSample.setText("0");
        JTextAttribute.setText("0");
        JTextClasses.setText("2");
        JlabelTrainSamples.setText("Number of different training samples:");
        jlblTrainExamp.setText("Ex:179:106:56");
        jtxtTrainsetInfo.setText("");
        JTextPopSize.setText("1000");
        JTextMaxGen.setText("50");
        JTextRun.setText("20");
        JTextMember.setText("3");
        JTextFunctions.setText("+:-:*:/:SQR:SQRT");
        JTextOperators.setText("Rand1:Rand2:Up1:Up2:Top1:Top2:Bottom1:Bottom2:Subs");
        JTextTrainSize.setText("0");
        JTextTestSize.setText("0");
        JlabelCRun.setText("Current Trial#:");
        JLabelRuleInfo.setText("Current Rule::");
        JLabelGenInfo.setText("Generation#:   Best Fitness:");
        JTextAreaRule.setText("");
    }

    void RunMethod()
    {
        if(Parameters.RunningMode)
            ExecuteMVGPC();
        else
        if(Parameters.ProcessMode)
            doPreprocess();
    }

    void ShowGeneFreq(JTable table)
    {
        GeneStat.sortGeneFreq();
        int nrow = table.getRowCount();
        for(int i = 0; i < nrow; i++)
        {
            table.getModel().setValueAt(Integer.toString(i + 1), i, 0);
            table.getModel().setValueAt("X" + GeneStat.getId(i), i, 1);
            table.getModel().setValueAt(Integer.toString(GeneStat.getFreq(i)), i, 2);
        }

    }

    void WriteTableHead(JTable table, String headrow)
    {
        StringTokenizer headers = new StringTokenizer(headrow, ":");
        int ncols = Math.min(headers.countTokens(), table.getColumnModel().getColumnCount());
        for(int i = 0; i < ncols; i++)
            table.getColumnModel().getColumn(i).setHeaderValue(headers.nextToken());

    }

    void disableNormcomp()
    {
        jRadioButtonLN.setEnabled(false);
        jRadioButtonLog10.setEnabled(false);
        jRadioButtonLinear.setEnabled(false);
        jLabelLRange.setEnabled(false);
        jTextFieldLRange.setEnabled(false);
    }

    void disableProcessComp()
    {
        jLabellth.setEnabled(false);
        jTextFieldLTH.setEnabled(false);
        jLabelhth.setEnabled(false);
        jTextFieldHTH.setEnabled(false);
        jLabelDiff.setEnabled(false);
        jTextFieldDiff.setEnabled(false);
        jLabelFold.setEnabled(false);
        jTextFieldFold.setEnabled(false);
    }

    void doPreprocess()
    {
        boolean firstlabels = jCheckBoxFrow.isSelected();
        boolean firstgeneid = jCheckBoxFcol.isSelected();
        String datafilename = jTextFieldGetDfile.getText();
        String outfilename = "DataOut.txt";
        if(jTextFieldOutPFile.getText() != null && jTextFieldOutPFile.getText().length() > 0 && jTextFieldOutPFile.getText().trim().length() > 0)
            outfilename = jTextFieldOutPFile.getText();
        float low = 10F;
        float high = 16000F;
        float diff = 50F;
        float fold = 5F;
        float a = 0.0F;
        float b = 0.0F;
        boolean normStatusOK = true;
        try
        {
            Preprocess ppdata = new Preprocess(firstlabels, firstgeneid);
            ppdata.readData(datafilename);
            if(jCheckBoxPrepro.isSelected())
            {
                low = Float.parseFloat(jTextFieldLTH.getText());
                high = Float.parseFloat(jTextFieldHTH.getText());
                diff = Float.parseFloat(jTextFieldDiff.getText());
                fold = Float.parseFloat(jTextFieldFold.getText());
                ppdata.preprocessData(low, high, diff, fold);
            }
            if(jCheckBoxNorm.isSelected())
            {
                if(jRadioButtonLN.isSelected())
                    normStatusOK = ppdata.logNormalization("e");
                else
                if(jRadioButtonLog10.isSelected())
                    normStatusOK = ppdata.logNormalization("10");
                else
                if(jRadioButtonLinear.isSelected())
                {
                    StringTokenizer st = new StringTokenizer(jTextFieldLRange.getText(), ":|");
                    a = Float.parseFloat(st.nextToken());
                    b = Float.parseFloat(st.nextToken());
                    ppdata.linearNormalization(a, b);
                }
            }
            if(normStatusOK)
            {
                String summary = ppdata.writeData(outfilename);
                jLabelStatus.setForeground(Color.BLUE);
                jLabelStatus.setText("Status: Done! " + summary);
            } else
            {
                jLabelStatus.setForeground(Color.RED);
                jLabelStatus.setText("Status: No log normalization as some values are <=0!");
            }
            if(Parameters.ProcessMode)
            {
                exitProcessRun();
                stop();
            }
        }
        catch(Exception ex)
        {
            jLabelStatus.setForeground(Color.RED);
            jLabelStatus.setText("Status: Error! " + ex.toString());
            exitProcessRun();
            stop();
        }
    }

    void doPreprocessAction(ActionEvent e)
    {
        String option = e.getActionCommand();
        if(jCheckBoxMGP.getActionCommand().compareTo(option) == 0)
        {
            if(jCheckBoxMGP.isSelected())
            {
                JlabelOperators.setEnabled(true);
                JTextOperators.setEnabled(true);
                JlabelDepth.setEnabled(true);
                JTextDepth.setEnabled(true);
            }
            else
            {
                JlabelOperators.setEnabled(false);
                JTextOperators.setEnabled(false);
                JlabelDepth.setEnabled(false);
                JTextDepth.setEnabled(false);
            }
        } else
        if(jCheckBoxPrepro.getActionCommand().compareTo(option) == 0)
        {
            if(jCheckBoxPrepro.isSelected())
                enableProcessComp();
            else
                disableProcessComp();
        } else
        if(option.compareTo(jCheckBoxNorm.getActionCommand()) == 0)
        {
            if(jCheckBoxNorm.isSelected())
                enableNormcomp();
            else
                disableNormcomp();
        } else
        if(jButtonProcess.getActionCommand().compareTo(option) == 0)
        {
            Parameters.ProcessMode = true;
            entryProcessRun();
            if(jCheckBoxPrepro.isSelected() || jCheckBoxNorm.isSelected())
            {
                stop();
                start();
                jLabelStatus.setForeground(Color.BLUE);
                jLabelStatus.setText("Status: Doing preprocessing!");
            } else
            {
                jLabelStatus.setForeground(Color.BLUE);
                jLabelStatus.setText("Status: Nothing has been done!");
                exitProcessRun();
            }
        } else
        if(jButtonPReset.getActionCommand().compareTo(option) == 0)
        {
            Parameters.ProcessMode = false;
            resetPreprocess();
            jLabelStatus.setBackground(Color.BLACK);
            jLabelStatus.setText("Status:");
            Parameters.ProcessMode = false;
        } else
        if(option.compareTo(jButtonPExit.getActionCommand()) == 0)
        {
            stop();
            System.exit(0);
        }
    }

    void enableNormcomp()
    {
        jRadioButtonLN.setEnabled(true);
        jRadioButtonLN.setSelected(true);
        jRadioButtonLog10.setEnabled(true);
        jRadioButtonLinear.setEnabled(true);
        jLabelLRange.setEnabled(true);
        jTextFieldLRange.setEnabled(true);
    }

    void enableProcessComp()
    {
        jLabellth.setEnabled(true);
        jTextFieldLTH.setEnabled(true);
        jLabelhth.setEnabled(true);
        jTextFieldHTH.setEnabled(true);
        jLabelDiff.setEnabled(true);
        jTextFieldDiff.setEnabled(true);
        jLabelFold.setEnabled(true);
        jTextFieldFold.setEnabled(true);
    }

    void entryEGPCRun()
    {
        for(int i = 0; i < jPanelPreProcess.getComponentCount(); i++)
        {
            Component s = jPanelPreProcess.getComponent(i);
            s.setEnabled(false);
        }

        for(int i = 0; i < jPanelMain.getComponentCount(); i++)
        {
            Component s = jPanelMain.getComponent(i);
            s.setEnabled(false);
        }

        JlabelCRun.setEnabled(true);
        JLabelRuleInfo.setEnabled(true);
        JLabelGenInfo.setEnabled(true);
        JlabelCBestRule.setEnabled(true);
        JTextAreaRule.setEnabled(true);
        JButtonStop.setEnabled(true);
        jButtonExit.setEnabled(true);
    }

    void entryProcessRun()
    {
        for(int i = 0; i < jPanelPreProcess.getComponentCount(); i++)
        {
            Component s = jPanelPreProcess.getComponent(i);
            s.setEnabled(false);
        }

        jLabelStatus.setEnabled(true);
        jButtonPExit.setEnabled(true);
        for(int i = 0; i < jPanelMain.getComponentCount(); i++)
        {
            Component s = jPanelMain.getComponent(i);
            s.setEnabled(false);
        }

    }

    void exitEGPCRun()
    {
        Parameters.RunningMode = false;
        for(int i = 0; i < jPanelPreProcess.getComponentCount(); i++)
        {
            Component s = jPanelPreProcess.getComponent(i);
            s.setEnabled(true);
        }

        for(int i = 0; i < jPanelMain.getComponentCount(); i++)
        {
            Component s = jPanelMain.getComponent(i);
            s.setEnabled(true);
        }

        JButtonStop.setEnabled(false);
        JButtonReset.setEnabled(true);
        JButtonRun.setEnabled(true);
        jButtonExit.setEnabled(true);
       
    }

    void exitProcessRun()
    {
        Parameters.ProcessMode = false;
        jButtonProcess.setEnabled(true);
        jButtonPReset.setEnabled(true);
        for(int i = 0; i < jPanelMain.getComponentCount(); i++)
        {
            Component s = jPanelMain.getComponent(i);
            s.setEnabled(true);
        }

    }

    private void jbInit()
        throws Exception
    {
        Font fontplain = new Font("Lucida Sans", 0, 12);
        Font fontbold = new Font("Lucida Sans", 1, 11);
        Font fontbold12 = new Font("Lucida Sans", 1, 12);
        jLabelmvgpcinfo.setFont(fontplain);
        contentPane = (JPanel)getContentPane();
        titledBorder1 = new TitledBorder("");
        titledBorder2 = new TitledBorder("");
        titledBorder3 = new TitledBorder("");
        titledBorder4 = new TitledBorder("");
        getContentPane().setBackground(Color.lightGray);
        setLocale(Locale.getDefault());
        setSize(new Dimension(623, 540));
        setTitle("MVGPC: a powerful tool for Cancer Classification!");
        contentPane.setBackground(new Color(236, 233, 248));
        contentPane.setEnabled(true);
        contentPane.setDebugGraphicsOptions(0);
        contentPane.setLayout(null);
        JlabelClasses.setFont(fontplain);
        JlabelClasses.setText("Classes:");
        JlabelClasses.setBounds(new Rectangle(318, 60, 72, 25));
        JlabelFileName.setFont(fontplain);
        JlabelFileName.setText("Data File Name:");
        JlabelFileName.setBounds(new Rectangle(260, 5, 106, 18));
        JlabelAttribute.setFont(fontplain);
        JlabelAttribute.setText("Attributes:");
        JlabelAttribute.setBounds(new Rectangle(161, 62, 77, 18));
        JlabelSample.setFont(fontplain);
        JlabelSample.setText("Samples:");
        JlabelSample.setBounds(new Rectangle(13, 61, 74, 18));
        JlabelTrainSamples.setEnabled(true);
        JlabelTrainSamples.setFont(fontplain);
        JlabelTrainSamples.setText("Number of different training samples: ");
        JlabelTrainSamples.setBounds(new Rectangle(9, 139, 232, 27));
        JlabelPopSize.setFont(fontplain);
        JlabelPopSize.setText("Population Size:");
        JlabelPopSize.setBounds(new Rectangle(10, 171, 103, 23));
        JlabelRun.setFont(fontplain);
        JlabelRun.setText("#Trials (Run):");
        JlabelRun.setBounds(new Rectangle(369, 170, 80, 25));
        JTextValidFile.setFont(fontplain);
        JTextValidFile.setBorder(BorderFactory.createEtchedBorder());
        JTextValidFile.setToolTipText("");
        JTextValidFile.setSelectionStart(4);
        JTextValidFile.setBounds(new Rectangle(245, 31, 232, 22));
        JTextSample.setText("0");
        JTextSample.setBounds(new Rectangle(94, 59, 47, 22));
        JTextAttribute.setText("0");
        JTextAttribute.setColumns(0);
        JTextAttribute.setBounds(new Rectangle(250, 60, 48, 22));
        JTextClasses.setText("2");
        JTextClasses.setBounds(new Rectangle(404, 60, 36, 23));
        jLabelHeadline.setFont(fontbold12);
        jLabelHeadline.setAlignmentX(0.0F);
        jLabelHeadline.setRequestFocusEnabled(true);
        jLabelHeadline.setHorizontalAlignment(0);
        jLabelHeadline.setText("MVGPC for Data Classification and Important Features Identification");
        jLabelHeadline.setBounds(new Rectangle(33, 9, 576, 27));
        JLabelTrainSize.setFont(fontplain);
        JLabelTrainSize.setRequestFocusEnabled(true);
        JLabelTrainSize.setText("Training Size:");
        JLabelTrainSize.setBounds(new Rectangle(266, 114, 81, 26));
        JTextTrainSize.setEnabled(true);
        JTextTrainSize.setBorder(titledBorder3);
        JTextTrainSize.setEditable(false);
        JTextTrainSize.setText("0");
        JTextTrainSize.setBounds(new Rectangle(360, 117, 50, 23));
        JTextTestSize.setText("");
        JTextTestSize.setEnabled(true);
        JTextTestSize.setBorder(titledBorder4);
        JTextTestSize.setEditable(false);
        JTextTestSize.setText("0");
        JTextTestSize.setBounds(new Rectangle(493, 116, 43, 23));
        JTextPopSize.setText("1000");
        JTextPopSize.setBounds(new Rectangle(116, 172, 62, 24));
        JlabelMaxGen.setFont(fontplain);
        JlabelMaxGen.setText("Max Generation:");
        JlabelMaxGen.setBounds(new Rectangle(191, 172, 109, 23));
        JlabelFunctions.setFont(fontplain);
        JlabelOperators.setFont(fontplain);
        JlabelDepth.setFont(fontplain);
        JlabelFunctions.setText("Functions:");
        JlabelOperators.setText("Operators:");
        JlabelOperators.setEnabled(false);
        JlabelDepth.setText("Operator Depth:");
        JlabelDepth.setEnabled(false);
        JlabelFunctions.setBounds(new Rectangle(12, 201, 83, 25));
        JlabelOperators.setBounds(new Rectangle(12, 232, 83, 25));
        JlabelDepth.setBounds(new Rectangle(12, 262, 100, 25));
        JlabelCRun.setFont(fontplain);
        JlabelCRun.setText("Current Trial#:");
        JlabelCRun.setBounds(new Rectangle(9, 320, 106, 22));
        JlabelMember.setFont(fontplain);
        JlabelMember.setText("Ensemble Size:");
        JlabelMember.setBounds(new Rectangle(390, 201, 88, 24));
        JTextMaxGen.setText("50");
        JTextMaxGen.setBounds(new Rectangle(311, 173, 45, 23));
        JTextRun.setText("20");
        JTextRun.setBounds(new Rectangle(470, 171, 44, 23));
        JTextMember.setText("3");
        JTextMember.setBounds(new Rectangle(497, 201, 41, 24));
        JTextFunctions.setText("+:-:*:/:SQR:SQRT");
        JTextOperators.setText("Rand1:Rand2:Up1:Up2:Top1:Top2:Bottom1:Bottom2:Subs");
        JTextDepth.setText("3");
        JTextDepth.setEnabled(false);
        JTextOperators.setEnabled(false);
        JTextFunctions.setBounds(new Rectangle(111, 202, 266, 25));
        JTextOperators.setBounds(new Rectangle(111, 232, 350, 25));
        JTextDepth.setBounds(new Rectangle(111, 262, 30, 25));
        JButtonRun.setBounds(new Rectangle(25, 420, 149, 25));
        JButtonRun.setFont(fontbold);
        JButtonRun.setText("Run MVGPC");
        JButtonRun.addActionListener(new AListener(this));
        JButtonStop.setBounds(new Rectangle(192, 420, 99, 26));
        JButtonStop.setEnabled(false);
        JButtonStop.setFont(fontbold);
        JButtonStop.setText("Stop");
        JButtonStop.addActionListener(new AListener(this));
        JButtonReset.setBounds(new Rectangle(305, 420, 106, 26));
        JButtonReset.setEnabled(false);
        JButtonReset.setFont(fontbold);
        JButtonReset.setText("Reset");
        JButtonReset.addActionListener(new AListener(this));
        JLabelGenInfo.setFont(fontplain);
        JLabelGenInfo.setText("Generation#:   Best Fitness:");
        JLabelGenInfo.setBounds(new Rectangle(284, 290, 271, 24));
        JLabelRuleInfo.setFont(fontplain);
        JLabelRuleInfo.setText("Current Rule::");
        JLabelRuleInfo.setBounds(new Rectangle(14, 290, 164, 24));
        JlabelCBestRule.setFont(fontplain);
        JlabelCBestRule.setText("Best Rule:");
        JlabelCBestRule.setBounds(new Rectangle(14, 350, 71, 29));
        JTextAreaRule.setBorder(titledBorder1);
        JTextAreaRule.setEditable(false);
        JTextAreaRule.setText("S-expression");
        JTextAreaRule.setLineWrap(true);
        jPanelMain.setLayout(null);
        jTabbedPaneMVGPC.setTabPlacement(1);
        jTabbedPaneMVGPC.setFont(fontbold12);
        jTabbedPaneMVGPC.setOpaque(false);
        jTabbedPaneMVGPC.setRequestFocusEnabled(true);
        jTabbedPaneMVGPC.setToolTipText("");
        jTabbedPaneMVGPC.setBounds(new Rectangle(14, 44, 571, 476));
        jPanelViewResults.setLayout(null);
        jRButtonFixed.setFont(fontplain);
        jRButtonFixed.setText("Fixed");
        jRButtonFixed.setBounds(new Rectangle(189, 115, 69, 23));
        jRButtonFixed.addActionListener(new AListener(this));
        jRButtonRandom.setFont(fontplain);
        jRButtonRandom.setSelected(true);
        jRButtonRandom.setText("Random");
        jRButtonRandom.setBounds(new Rectangle(106, 115, 77, 23));
        jRButtonRandom.addActionListener(new AListener(this));
        jRButtonMicroarray.setFont(fontplain);
        jRButtonMicroarray.setActionCommand("Microarray");
        jRButtonMicroarray.setSelected(true);
        jRButtonMicroarray.setText("Microarray");
        jRButtonMicroarray.setBounds(new Rectangle(75, 6, 97, 23));
        jRButtonMicroarray.addActionListener(new AListener(this));
        jRButtonUCIML.setFont(fontplain);
        jRButtonUCIML.setText("UCI ML");
        jRButtonUCIML.setBounds(new Rectangle(173, 4, 70, 24));
        jRButtonUCIML.addActionListener(new AListener(this));
        jLabelFileType.setFont(fontplain);
        jLabelFileType.setText("File Type:");
        jLabelFileType.setBounds(new Rectangle(11, 6, 57, 22));
        jLabelSplitType.setFont(fontplain);
        jLabelSplitType.setText("Data Split Type:");
        jLabelSplitType.setBounds(new Rectangle(13, 113, 92, 27));
        jLabelTextsize.setFont(fontplain);
        jLabelTextsize.setText("Test Size:");
        jLabelTextsize.setBounds(new Rectangle(419, 117, 56, 23));
        jScrollPaneSolution.setVerticalScrollBarPolicy(22);
        jScrollPaneSolution.setRequestFocusEnabled(false);
        jScrollPaneSolution.setBounds(new Rectangle(126, 320, 410, 67));
        jProgressBarTask.setStringPainted(true);
        jProgressBarTask.setBounds(new Rectangle(11, 390, 524, 21));
        jPanelGene.setLayout(null);
        jScrollPaneGene.setColumnHeader(null);
        jScrollPaneGene.setBounds(new Rectangle(36, 49, 446, 301));
        jlblAttrTypes.setFont(fontplain);
        jlblAttrTypes.setText("Attributes Type:");
        jlblAttrTypes.setBounds(new Rectangle(11, 87, 102, 25));
        jtxtAttrTypes.setText("N1:30");
        jtxtAttrTypes.setBounds(new Rectangle(118, 88, 182, 21));
        jlblAttrInfo.setFont(fontplain);
        jlblAttrInfo.setText("(N=numeric, L=nominal, B=boolean)");
        jlblAttrInfo.setBounds(new Rectangle(303, 87, 246, 21));
        jlblTableTitle.setText("Number of correct predictions and accuracies by SR/SSR and MVGPC");
        jlblTableTitle.setBounds(new Rectangle(29, 1, 517, 35));
        jlblTableTitle.setFont(fontbold12);
        jlblTableTitle.setHorizontalAlignment(0);
        jblGTableTitle.setFont(fontbold12);
        jblGTableTitle.setHorizontalAlignment(0);
        jblGTableTitle.setText("Ranking of genes/features (more frequently selected)");
        jblGTableTitle.setBounds(new Rectangle(22, 15, 514, 25));
        jblGTableMore.setText("Frequencies of other genes (if any) are available in GeneFreq...file.");
        jblGTableMore.setBounds(new Rectangle(29, 361, 493, 25));
        jblGTableMore.setFont(fontbold12);
        jblGTableMore.setHorizontalAlignment(0);
        jlblTrainExamp.setFont(fontplain);
        jlblTrainExamp.setText("Ex: 179:106:56");
        jlblTrainExamp.setBounds(new Rectangle(410, 144, 129, 21));
        jtxtTrainsetInfo.setFont(fontplain);
        jtxtTrainsetInfo.setText("");
        jtxtTrainsetInfo.setBounds(new Rectangle(261, 143, 146, 22));
        jComboBoxDataFile.setFont(fontplain);
        jComboBoxDataFile.setEditable(true);
        jComboBoxDataFile.setEnabled(true);
        jComboBoxDataFile.setSelectedIndex(-1);
        jComboBoxDataFile.setBounds(new Rectangle(379, 5, 165, 22));
        jComboBoxDataFile.addActionListener(new AListener(this));
        jLabelValidFile.setFont(fontplain);
        jLabelValidFile.setText("Validation (independent test) File:");
        jLabelValidFile.setBounds(new Rectangle(12, 30, 217, 21));
        jLabelMStat.setFont(fontplain);
        jLabelMStat.setForeground(Color.red);
        jLabelMStat.setOpaque(true);
        jLabelMStat.setText("MVGPC:");
        jLabelMStat.setBounds(new Rectangle(31, 330, 444, 22));
        jLabelPStat.setFont(fontplain);
        jLabelPStat.setForeground(Color.blue);
        jLabelPStat.setText("SR=Single Rule; SSR=Single Set of Rules");
        jLabelPStat.setBounds(new Rectangle(32, 356, 453, 22));
        jCancer.setFont(fontplain);
        jCancer.setForeground(Color.blue);
        jCancer.setText(" ");
        jCancer.setBounds(new Rectangle(32, 400, 453, 22));
        jPanelViewResults.setBorder(null);
        jPanelViewResults.setDebugGraphicsOptions(0);
        jPanelAbout.setLayout(null);
        jLabelmvgpcinfo.setText("jLabel4");
        jLabelmvgpcinfo.setBounds(new Rectangle(19, 20, 492, 356));
        jScrollPaneViewResults.setBounds(new Rectangle(32, 37, 490, 288));
        jButtonExit.setBounds(new Rectangle(429, 420, 93, 26));
        jButtonExit.setFont(fontbold);
        jButtonExit.setText("Exit");
        jButtonExit.addActionListener(new AListener(this));
        jPanelPreProcess.setLayout(null);
        jCheckBoxNorm.setFont(fontbold);
        jCheckBoxNorm.setText("Normalize Data");
        jCheckBoxNorm.setBounds(new Rectangle(29, 237, 131, 22));
        jCheckBoxNorm.addActionListener(new PreprocessAction(this));
        jRadioButtonLN.setEnabled(false);
        jRadioButtonLN.setFont(fontplain);
        jRadioButtonLN.setPreferredSize(new Dimension(91, 23));
        jRadioButtonLN.setFocusPainted(true);
        jRadioButtonLN.setSelected(true);
        jRadioButtonLN.setText("Natural logarithm");
        jRadioButtonLN.setBounds(new Rectangle(31, 275, 139, 27));
        jRadioButtonLog10.setEnabled(false);
        jRadioButtonLog10.setFont(fontplain);
        jRadioButtonLog10.setText("log10");
        jRadioButtonLog10.setBounds(new Rectangle(180, 274, 69, 28));
        jRadioButtonLinear.setEnabled(false);
        jRadioButtonLinear.setFont(fontplain);
        jRadioButtonLinear.setText("Linear");
        jRadioButtonLinear.setBounds(new Rectangle(261, 278, 81, 25));
        jTextFieldLRange.setEnabled(false);
        jTextFieldLRange.setSelectionStart(11);
        jTextFieldLRange.setText("0:1");
        jTextFieldLRange.setBounds(new Rectangle(450, 280, 61, 21));
        jLabelLRange.setEnabled(false);
        jLabelLRange.setFont(fontplain);
        jLabelLRange.setText("Range (a:b):");
        jLabelLRange.setBounds(new Rectangle(354, 278, 85, 25));
        jCheckBoxPrepro.setFont(fontbold);
        jCheckBoxMGP.setFont(fontbold);
        jCheckBoxPrepro.setText("Preprocess Data");
        jCheckBoxMGP.setText("MGP");
        jCheckBoxPrepro.setBounds(new Rectangle(27, 131, 144, 24));
        jCheckBoxMGP.setBounds(new Rectangle(470, 232, 100, 25));
        jCheckBoxPrepro.addActionListener(new PreprocessAction(this));
        jCheckBoxMGP.addActionListener(new PreprocessAction(this));
        jLabellth.setEnabled(false);
        jLabellth.setFont(fontplain);
        jLabellth.setText("Lower threshold:");
        jLabellth.setBounds(new Rectangle(27, 163, 98, 22));
        jTextFieldLTH.setEnabled(false);
        jTextFieldLTH.setText("10");
        jTextFieldLTH.setBounds(new Rectangle(139, 164, 39, 26));
        jLabelhth.setBounds(new Rectangle(184, 166, 104, 23));
        jLabelhth.setText("Higher threshold:");
        jLabelhth.setEnabled(false);
        jLabelhth.setFont(fontplain);
        jTextFieldHTH.setEnabled(false);
        jTextFieldHTH.setText("16000");
        jTextFieldHTH.setBounds(new Rectangle(293, 166, 57, 24));
        jLabelDiff.setEnabled(false);
        jLabelDiff.setFont(fontplain);
        jLabelDiff.setText("Difference:");
        jLabelDiff.setBounds(new Rectangle(28, 203, 64, 19));
        jTextFieldDiff.setEnabled(false);
        jTextFieldDiff.setText("100");
        jTextFieldDiff.setBounds(new Rectangle(113, 199, 55, 24));
        jLabelFold.setEnabled(false);
        jLabelFold.setFont(fontplain);
        jLabelFold.setRequestFocusEnabled(true);
        jLabelFold.setText("Fold:");
        jLabelFold.setBounds(new Rectangle(193, 200, 45, 24));
        jTextFieldFold.setEnabled(false);
        jTextFieldFold.setText("3");
        jTextFieldFold.setBounds(new Rectangle(242, 200, 60, 24));
        jButtonProcess.setBounds(new Rectangle(37, 323, 182, 28));
        jButtonProcess.setFont(fontbold);
        jButtonProcess.setText("Perform Preprocessing");
        jButtonProcess.addActionListener(new PreprocessAction(this));
        jButtonPReset.setBounds(new Rectangle(225, 324, 113, 28));
        jButtonPReset.setFont(fontbold);
        jButtonPReset.setText("Reset");
        jButtonPReset.addActionListener(new PreprocessAction(this));
        jLabelDfile.setFont(fontbold);
        jLabelDfile.setText("Data file name:");
        jLabelDfile.setBounds(new Rectangle(42, 15, 99, 23));
        jTextFieldGetDfile.setText("");
        jTextFieldGetDfile.setBounds(new Rectangle(154, 13, 267, 25));
        jLabelOutPFile.setFont(fontplain);
        jLabelOutPFile.setText("Output file name:");
        jLabelOutPFile.setBounds(new Rectangle(40, 46, 109, 24));
        jTextFieldOutPFile.setText("");
        jTextFieldOutPFile.setBounds(new Rectangle(157, 48, 232, 23));
        jLabelStatus.setFont(fontplain);
        jLabelStatus.setText("Status:");
        jLabelStatus.setBounds(new Rectangle(29, 362, 525, 25));
        
        jLabelDefOutfile.setFont(fontplain);
        jLabelDefOutfile.setText("Default: DataOut.txt");
        jLabelDefOutfile.setBounds(new Rectangle(405, 48, 140, 25));
        jCheckBoxFcol.setFont(fontplain);
        jCheckBoxFcol.setActionCommand("jCheckBox1");
        jCheckBoxFcol.setText("First column contains genes' IDs");
        jCheckBoxFcol.setBounds(new Rectangle(26, 98, 230, 18));
        jCheckBoxFrow.setFont(fontplain);
        jCheckBoxFrow.setText("First row contains samples' labels");
        jCheckBoxFrow.setBounds(new Rectangle(277, 97, 236, 23));
        jButtonPExit.setBounds(new Rectangle(347, 324, 100, 27));
        jButtonPExit.setFont(fontbold);
        jButtonPExit.setText("Exit");
        jButtonPExit.addActionListener(new PreprocessAction(this));
        jPanelAbout.setFont(fontplain);
        jLabelExcludeGene.setFont(fontplain);
        jLabelExcludeGene.setText("<HTML>Those genes are excluded that violate variation filters: Max(g)-Min(g)&gt;Difference and Max(g)/Min(g)&gt;Fold .</HTML>");
        jLabelExcludeGene.setBounds(new Rectangle(366, 142, 182, 100));
        buttonGroupSplit.add(jRButtonFixed);
        buttonGroupSplit.add(jRButtonRandom);
        jPanelMain.add(jScrollPaneSolution, null);
        jScrollPaneSolution.getViewport().add(JTextAreaRule, null);
        jPanelMain.add(JlabelSample, null);
        jPanelMain.add(JlabelFunctions, null);
        jPanelMain.add(JlabelOperators,null);
        jPanelMain.add(JlabelDepth,null);
        jPanelMain.add(jlblAttrTypes, null);
        jPanelMain.add(jtxtAttrTypes, null);
        jPanelMain.add(jRButtonRandom, null);
        jPanelMain.add(jLabelSplitType, null);
        jPanelMain.add(jlblAttrInfo, null);
        jPanelMain.add(JlabelTrainSamples, null);
        jPanelMain.add(jtxtTrainsetInfo, null);
        jPanelMain.add(jlblTrainExamp, null);
        jPanelMain.add(jRButtonMicroarray, null);
        jPanelMain.add(jLabelValidFile, null);
        jPanelMain.add(JlabelMaxGen, null);
        jPanelMain.add(jComboBoxDataFile, null);
        jPanelMain.add(jLabelFileType, null);
        jPanelMain.add(jRButtonUCIML, null);
        jPanelMain.add(JlabelFileName, null);
        jPanelMain.add(JTextValidFile, null);
        jPanelMain.add(JTextTestSize, null);
        jPanelMain.add(jLabelTextsize, null);
        jPanelMain.add(JTextTrainSize, null);
        jPanelMain.add(JLabelTrainSize, null);
        jPanelMain.add(jRButtonFixed, null);
        jPanelMain.add(JTextRun, null);
        jPanelMain.add(JlabelRun, null);
        jPanelMain.add(JTextMaxGen, null);
        jPanelMain.add(JTextPopSize, null);
        jPanelMain.add(JlabelPopSize, null);
        jPanelMain.add(JlabelCRun, null);
        jPanelMain.add(JLabelRuleInfo, null);
        jPanelMain.add(JlabelCBestRule, null);
        jPanelMain.add(JLabelGenInfo, null);
        jPanelMain.add(JTextClasses, null);
        jPanelMain.add(JlabelClasses, null);
        jPanelMain.add(JTextAttribute, null);
        jPanelMain.add(JlabelAttribute, null);
        jPanelMain.add(JTextSample, null);
        jPanelMain.add(JTextMember, null);
        jPanelMain.add(JlabelMember, null);
        jPanelMain.add(JTextFunctions, null);
        jPanelMain.add(JTextOperators,null);
        jPanelMain.add(JTextDepth,null);
        jPanelMain.add(JButtonRun, null);
        jPanelMain.add(JButtonStop, null);
        jPanelMain.add(JButtonReset, null);
        jPanelMain.add(jButtonExit, null);
        jPanelMain.add(jProgressBarTask, null);
        jPanelMain.add(jCancer, null);
       
        jTabbedPaneMVGPC.add(jPanelAbout, "About MVGPC");
        jTabbedPaneMVGPC.add(jPanelPreProcess, "Preprocess Data");
        jTabbedPaneMVGPC.add(jPanelMain, "Run MVGPC");
        jTabbedPaneMVGPC.add(jPanelViewResults, "View Accuracy");
        jTabbedPaneMVGPC.add(jPanelGene, "Feature Ranking");
        jPanelGene.add(jScrollPaneGene, null);
        jPanelGene.add(jblGTableTitle, null);
        jPanelGene.add(jblGTableMore, null);
        contentPane.add(jTabbedPaneMVGPC, null);
        buttonGroupFileType.add(jRButtonUCIML);
        buttonGroupFileType.add(jRButtonMicroarray);
        jPanelViewResults.add(jlblTableTitle, null);
        jPanelViewResults.add(jScrollPaneViewResults, null);
        jPanelViewResults.add(jLabelMStat, null);
        jPanelViewResults.add(jLabelPStat, null);
        //jTabbedPaneMVGPC.add(jCancer, null);
        jPanelAbout.add(jLabelmvgpcinfo, null);
        contentPane.add(jLabelHeadline, null);
        DisplayMessage();
        jLabelmvgpcinfo.setVerticalAlignment(1);
        buttonGroupNorm.add(jRadioButtonLN);
        buttonGroupNorm.add(jRadioButtonLog10);
        buttonGroupNorm.add(jRadioButtonLinear);
        jPanelPreProcess.add(jLabelDfile, null);
        jPanelPreProcess.add(jTextFieldOutPFile, null);
        jPanelPreProcess.add(jLabelOutPFile, null);
        jPanelPreProcess.add(jLabelDefOutfile, null);
        jPanelPreProcess.add(jButtonProcess, null);
        jPanelPreProcess.add(jButtonPReset, null);
        jPanelPreProcess.add(jCheckBoxNorm, null);
        jPanelPreProcess.add(jTextFieldFold, null);
        jPanelPreProcess.add(jCheckBoxPrepro, null);
        jPanelMain.add(jCheckBoxMGP, null);
        jPanelPreProcess.add(jLabellth, null);
        jPanelPreProcess.add(jTextFieldDiff, null);
        jPanelPreProcess.add(jLabelFold, null);
        jPanelPreProcess.add(jCheckBoxFcol, null);
        jPanelPreProcess.add(jLabelStatus, null);
        jPanelPreProcess.add(jButtonPExit, null);
        jPanelPreProcess.add(jCheckBoxFrow, null);
        jPanelPreProcess.add(jTextFieldLTH, null);
        jPanelPreProcess.add(jTextFieldHTH, null);
        jPanelPreProcess.add(jLabelhth, null);
        jPanelPreProcess.add(jLabelDiff, null);
        jPanelPreProcess.add(jTextFieldLRange, null);
        jPanelPreProcess.add(jRadioButtonLinear, null);
        jPanelPreProcess.add(jLabelLRange, null);
        jPanelPreProcess.add(jRadioButtonLog10, null);
        jPanelPreProcess.add(jRadioButtonLN, null);
        jPanelPreProcess.add(jTextFieldGetDfile, null);
        jPanelPreProcess.add(jLabelExcludeGene, null);
    }

    protected void processWindowEvent(WindowEvent e)
    {
        super.processWindowEvent(e);
        if(e.getID() == 201)
            System.exit(0);
    }

    void resetPreprocess()
    {
        for(int i = 0; i < jPanelPreProcess.getComponentCount(); i++)
        {
            Component s = jPanelPreProcess.getComponent(i);
            s.setEnabled(true);
        }
       
        jCheckBoxPrepro.setSelected(false);
        jCheckBoxNorm.setSelected(false);
        jCheckBoxFcol.setSelected(false);
        jCheckBoxFrow.setSelected(false);
        disableNormcomp();
        disableProcessComp();
    }

    public void run()
    {
        RunMethod();
    }

    public void start()
    {
        if(th == null)
        {
            th = new Thread(this, "MVGPC");
            th.start();
        }
    }

    public void stop()
    {
        if(th != null)
            th = null;
    }

    boolean EXAMPLE;
    boolean FIXEDSPLIT;
    JButton JButtonReset;
    JButton JButtonRun;
    JButton JButtonStop;
    JLabel JLabelGenInfo;
    JLabel JLabelRuleInfo;
    JLabel JLabelTrainSize;
    JTextArea JTextAreaRule;
    JTextField JTextAttribute;
    JTextField JTextClasses;
    JTextField JTextFunctions;
    JTextField JTextOperators;
    JTextField JTextDepth;
    JTextField JTextMaxGen;
    JTextField JTextMember;
    JTextField JTextPopSize;
    JTextField JTextRun;
    JTextField JTextSample;
    JTextField JTextTestSize;
    JTextField JTextTrainSize;
    JTextField JTextValidFile;
    JLabel JlabelAttribute;
    JLabel JlabelCBestRule;
    JLabel JlabelCRun;
    JLabel JlabelClasses;
    JLabel JlabelFileName;
    JLabel JlabelFunctions;
    JLabel JlabelOperators;
    JLabel JlabelDepth;
    JLabel JlabelMaxGen;
    JLabel JlabelMember;
    JLabel JlabelPopSize;
    JLabel JlabelRun;
    JLabel JlabelSample;
    JLabel JlabelTrainSamples;
    boolean UCIML;
    ButtonGroup buttonGroupFileType;
    ButtonGroup buttonGroupNorm;
    ButtonGroup buttonGroupSplit;
    JPanel contentPane;
    String examplefiles[];
    JButton jButtonExit;
    JButton jButtonPExit;
    JButton jButtonPReset;
    JButton jButtonProcess;
    JCheckBox jCheckBoxFcol;
    JCheckBox jCheckBoxFrow;
    JCheckBox jCheckBoxNorm;
    JCheckBox jCheckBoxPrepro;
    JCheckBox jCheckBoxMGP;
    JComboBox jComboBoxDataFile;
    JLabel jLabelDefOutfile;
    JLabel jLabelDfile;
    JLabel jLabelDiff;
    JLabel jLabelExcludeGene;
    JLabel jLabelFileType;
    JLabel jLabelFold;
    JLabel jLabelHeadline;
    JLabel jLabelLRange;
    JLabel jLabelMStat;
    JLabel jLabelOutPFile;
    JLabel jLabelPStat;
    JLabel jCancer;
    JLabel jLabelSplitType;
    JLabel jLabelStatus;
   
    JLabel jLabelTextsize;
    JLabel jLabelValidFile;
    JLabel jLabelhth;
    JLabel jLabellth;
    JLabel jLabelmvgpcinfo;
    JPanel jPanelAbout;
    JPanel jPanelGene;
    JPanel jPanelMain;
    JPanel jPanelPreProcess;
    JPanel jPanelViewResults;
    JProgressBar jProgressBarTask;
    JRadioButton jRButtonFixed;
    JRadioButton jRButtonMicroarray;
    JRadioButton jRButtonRandom;
    JRadioButton jRButtonUCIML;
    JRadioButton jRadioButtonLN;
    JRadioButton jRadioButtonLinear;
    JRadioButton jRadioButtonLog10;
    JScrollPane jScrollPaneGene;
    JScrollPane jScrollPaneSolution;
    JScrollPane jScrollPaneViewResults;
    JTabbedPane jTabbedPaneMVGPC;
    JTextField jTextFieldDiff;
    JTextField jTextFieldFold;
    JTextField jTextFieldGetDfile;
    JTextField jTextFieldHTH;
    JTextField jTextFieldLRange;
    JTextField jTextFieldLTH;
    JTextField jTextFieldOutPFile;
    JLabel jblGTableMore;
    JLabel jblGTableTitle;
    JLabel jlblAttrInfo;
    JLabel jlblAttrTypes;
    JLabel jlblTableTitle;
    JLabel jlblTrainExamp;
    JTextField jtxtAttrTypes;
    JTextField jtxtTrainsetInfo;
    Thread th;
    TitledBorder titledBorder1;
    TitledBorder titledBorder2;
    TitledBorder titledBorder3;
    TitledBorder titledBorder4;
}
