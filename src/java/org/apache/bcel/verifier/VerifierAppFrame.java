begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Copyright  2000-2004 The Apache Software Foundation  *  *  Licensed under the Apache License, Version 2.0 (the "License");   *  you may not use this file except in compliance with the License.  *  You may obtain a copy of the License at  *  *      http://www.apache.org/licenses/LICENSE-2.0  *  *  Unless required by applicable law or agreed to in writing, software  *  distributed under the License is distributed on an "AS IS" BASIS,  *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *  See the License for the specific language governing permissions and  *  limitations under the License.   *  */
end_comment

begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|verifier
package|;
end_package

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|AWTEvent
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|CardLayout
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Color
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Dimension
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|GridLayout
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|event
operator|.
name|ActionEvent
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|event
operator|.
name|WindowEvent
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|BorderFactory
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JFrame
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JList
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JMenu
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JMenuBar
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JMenuItem
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JOptionPane
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JPanel
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JScrollPane
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JSplitPane
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JTextPane
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|ListSelectionModel
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|event
operator|.
name|ListSelectionEvent
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|Repository
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|classfile
operator|.
name|JavaClass
import|;
end_import

begin_comment
comment|/**  * This class implements a machine-generated frame for use with  * the GraphicalVerfifier.  *  * @version $Id$  * @author Enver Haase  * @see GraphicalVerifier  */
end_comment

begin_class
specifier|public
class|class
name|VerifierAppFrame
extends|extends
name|JFrame
block|{
name|JPanel
name|contentPane
decl_stmt|;
name|JSplitPane
name|jSplitPane1
init|=
operator|new
name|JSplitPane
argument_list|()
decl_stmt|;
name|JPanel
name|jPanel1
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|JPanel
name|jPanel2
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|JSplitPane
name|jSplitPane2
init|=
operator|new
name|JSplitPane
argument_list|()
decl_stmt|;
name|JPanel
name|jPanel3
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|JList
name|classNamesJList
init|=
operator|new
name|JList
argument_list|()
decl_stmt|;
name|GridLayout
name|gridLayout1
init|=
operator|new
name|GridLayout
argument_list|()
decl_stmt|;
name|JPanel
name|messagesPanel
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|GridLayout
name|gridLayout2
init|=
operator|new
name|GridLayout
argument_list|()
decl_stmt|;
name|JMenuBar
name|jMenuBar1
init|=
operator|new
name|JMenuBar
argument_list|()
decl_stmt|;
name|JMenu
name|jMenu1
init|=
operator|new
name|JMenu
argument_list|()
decl_stmt|;
name|JScrollPane
name|jScrollPane1
init|=
operator|new
name|JScrollPane
argument_list|()
decl_stmt|;
name|JScrollPane
name|messagesScrollPane
init|=
operator|new
name|JScrollPane
argument_list|()
decl_stmt|;
name|JScrollPane
name|jScrollPane3
init|=
operator|new
name|JScrollPane
argument_list|()
decl_stmt|;
name|GridLayout
name|gridLayout4
init|=
operator|new
name|GridLayout
argument_list|()
decl_stmt|;
name|JScrollPane
name|jScrollPane4
init|=
operator|new
name|JScrollPane
argument_list|()
decl_stmt|;
name|CardLayout
name|cardLayout1
init|=
operator|new
name|CardLayout
argument_list|()
decl_stmt|;
specifier|private
name|String
name|JUSTICE_VERSION
init|=
literal|"JustIce by Enver Haase"
decl_stmt|;
specifier|private
name|String
name|current_class
decl_stmt|;
name|GridLayout
name|gridLayout3
init|=
operator|new
name|GridLayout
argument_list|()
decl_stmt|;
name|JTextPane
name|pass1TextPane
init|=
operator|new
name|JTextPane
argument_list|()
decl_stmt|;
name|JTextPane
name|pass2TextPane
init|=
operator|new
name|JTextPane
argument_list|()
decl_stmt|;
name|JTextPane
name|messagesTextPane
init|=
operator|new
name|JTextPane
argument_list|()
decl_stmt|;
name|JMenuItem
name|newFileMenuItem
init|=
operator|new
name|JMenuItem
argument_list|()
decl_stmt|;
name|JSplitPane
name|jSplitPane3
init|=
operator|new
name|JSplitPane
argument_list|()
decl_stmt|;
name|JSplitPane
name|jSplitPane4
init|=
operator|new
name|JSplitPane
argument_list|()
decl_stmt|;
name|JScrollPane
name|jScrollPane2
init|=
operator|new
name|JScrollPane
argument_list|()
decl_stmt|;
name|JScrollPane
name|jScrollPane5
init|=
operator|new
name|JScrollPane
argument_list|()
decl_stmt|;
name|JScrollPane
name|jScrollPane6
init|=
operator|new
name|JScrollPane
argument_list|()
decl_stmt|;
name|JScrollPane
name|jScrollPane7
init|=
operator|new
name|JScrollPane
argument_list|()
decl_stmt|;
name|JList
name|pass3aJList
init|=
operator|new
name|JList
argument_list|()
decl_stmt|;
name|JList
name|pass3bJList
init|=
operator|new
name|JList
argument_list|()
decl_stmt|;
name|JTextPane
name|pass3aTextPane
init|=
operator|new
name|JTextPane
argument_list|()
decl_stmt|;
name|JTextPane
name|pass3bTextPane
init|=
operator|new
name|JTextPane
argument_list|()
decl_stmt|;
name|JMenu
name|jMenu2
init|=
operator|new
name|JMenu
argument_list|()
decl_stmt|;
name|JMenuItem
name|whatisMenuItem
init|=
operator|new
name|JMenuItem
argument_list|()
decl_stmt|;
name|JMenuItem
name|aboutMenuItem
init|=
operator|new
name|JMenuItem
argument_list|()
decl_stmt|;
comment|/** Constructor. */
specifier|public
name|VerifierAppFrame
parameter_list|()
block|{
name|enableEvents
argument_list|(
name|AWTEvent
operator|.
name|WINDOW_EVENT_MASK
argument_list|)
expr_stmt|;
try|try
block|{
name|jbInit
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
comment|/** Initizalization of the components. */
specifier|private
name|void
name|jbInit
parameter_list|()
throws|throws
name|Exception
block|{
comment|//setIconImage(Toolkit.getDefaultToolkit().createImage(Frame1.class.getResource("[Ihr Symbol]")));
name|contentPane
operator|=
operator|(
name|JPanel
operator|)
name|this
operator|.
name|getContentPane
argument_list|()
expr_stmt|;
name|contentPane
operator|.
name|setLayout
argument_list|(
name|cardLayout1
argument_list|)
expr_stmt|;
name|this
operator|.
name|setJMenuBar
argument_list|(
name|jMenuBar1
argument_list|)
expr_stmt|;
name|this
operator|.
name|setSize
argument_list|(
operator|new
name|Dimension
argument_list|(
literal|708
argument_list|,
literal|451
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|setTitle
argument_list|(
literal|"JustIce"
argument_list|)
expr_stmt|;
name|jPanel1
operator|.
name|setMinimumSize
argument_list|(
operator|new
name|Dimension
argument_list|(
literal|100
argument_list|,
literal|100
argument_list|)
argument_list|)
expr_stmt|;
name|jPanel1
operator|.
name|setPreferredSize
argument_list|(
operator|new
name|Dimension
argument_list|(
literal|100
argument_list|,
literal|100
argument_list|)
argument_list|)
expr_stmt|;
name|jPanel1
operator|.
name|setLayout
argument_list|(
name|gridLayout1
argument_list|)
expr_stmt|;
name|jSplitPane2
operator|.
name|setOrientation
argument_list|(
name|JSplitPane
operator|.
name|VERTICAL_SPLIT
argument_list|)
expr_stmt|;
name|jPanel2
operator|.
name|setLayout
argument_list|(
name|gridLayout2
argument_list|)
expr_stmt|;
name|jPanel3
operator|.
name|setMinimumSize
argument_list|(
operator|new
name|Dimension
argument_list|(
literal|200
argument_list|,
literal|100
argument_list|)
argument_list|)
expr_stmt|;
name|jPanel3
operator|.
name|setPreferredSize
argument_list|(
operator|new
name|Dimension
argument_list|(
literal|400
argument_list|,
literal|400
argument_list|)
argument_list|)
expr_stmt|;
name|jPanel3
operator|.
name|setLayout
argument_list|(
name|gridLayout4
argument_list|)
expr_stmt|;
name|messagesPanel
operator|.
name|setMinimumSize
argument_list|(
operator|new
name|Dimension
argument_list|(
literal|100
argument_list|,
literal|100
argument_list|)
argument_list|)
expr_stmt|;
name|messagesPanel
operator|.
name|setLayout
argument_list|(
name|gridLayout3
argument_list|)
expr_stmt|;
name|jPanel2
operator|.
name|setMinimumSize
argument_list|(
operator|new
name|Dimension
argument_list|(
literal|200
argument_list|,
literal|100
argument_list|)
argument_list|)
expr_stmt|;
name|jMenu1
operator|.
name|setText
argument_list|(
literal|"File"
argument_list|)
expr_stmt|;
name|jScrollPane1
operator|.
name|getViewport
argument_list|()
operator|.
name|setBackground
argument_list|(
name|Color
operator|.
name|red
argument_list|)
expr_stmt|;
name|messagesScrollPane
operator|.
name|getViewport
argument_list|()
operator|.
name|setBackground
argument_list|(
name|Color
operator|.
name|red
argument_list|)
expr_stmt|;
name|messagesScrollPane
operator|.
name|setPreferredSize
argument_list|(
operator|new
name|Dimension
argument_list|(
literal|10
argument_list|,
literal|10
argument_list|)
argument_list|)
expr_stmt|;
name|classNamesJList
operator|.
name|addListSelectionListener
argument_list|(
operator|new
name|javax
operator|.
name|swing
operator|.
name|event
operator|.
name|ListSelectionListener
argument_list|()
block|{
specifier|public
name|void
name|valueChanged
parameter_list|(
name|ListSelectionEvent
name|e
parameter_list|)
block|{
name|classNamesJList_valueChanged
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|classNamesJList
operator|.
name|setSelectionMode
argument_list|(
name|ListSelectionModel
operator|.
name|SINGLE_SELECTION
argument_list|)
expr_stmt|;
name|jScrollPane3
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createLineBorder
argument_list|(
name|Color
operator|.
name|black
argument_list|)
argument_list|)
expr_stmt|;
name|jScrollPane3
operator|.
name|setPreferredSize
argument_list|(
operator|new
name|Dimension
argument_list|(
literal|100
argument_list|,
literal|100
argument_list|)
argument_list|)
expr_stmt|;
name|gridLayout4
operator|.
name|setRows
argument_list|(
literal|4
argument_list|)
expr_stmt|;
name|gridLayout4
operator|.
name|setColumns
argument_list|(
literal|1
argument_list|)
expr_stmt|;
name|gridLayout4
operator|.
name|setHgap
argument_list|(
literal|1
argument_list|)
expr_stmt|;
name|jScrollPane4
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createLineBorder
argument_list|(
name|Color
operator|.
name|black
argument_list|)
argument_list|)
expr_stmt|;
name|jScrollPane4
operator|.
name|setPreferredSize
argument_list|(
operator|new
name|Dimension
argument_list|(
literal|100
argument_list|,
literal|100
argument_list|)
argument_list|)
expr_stmt|;
name|pass1TextPane
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createRaisedBevelBorder
argument_list|()
argument_list|)
expr_stmt|;
name|pass1TextPane
operator|.
name|setToolTipText
argument_list|(
literal|""
argument_list|)
expr_stmt|;
name|pass1TextPane
operator|.
name|setEditable
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|pass2TextPane
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createRaisedBevelBorder
argument_list|()
argument_list|)
expr_stmt|;
name|pass2TextPane
operator|.
name|setEditable
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|messagesTextPane
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createRaisedBevelBorder
argument_list|()
argument_list|)
expr_stmt|;
name|messagesTextPane
operator|.
name|setEditable
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|newFileMenuItem
operator|.
name|setText
argument_list|(
literal|"New..."
argument_list|)
expr_stmt|;
name|newFileMenuItem
operator|.
name|setAccelerator
argument_list|(
name|javax
operator|.
name|swing
operator|.
name|KeyStroke
operator|.
name|getKeyStroke
argument_list|(
literal|78
argument_list|,
name|java
operator|.
name|awt
operator|.
name|event
operator|.
name|KeyEvent
operator|.
name|CTRL_MASK
argument_list|,
literal|true
argument_list|)
argument_list|)
expr_stmt|;
name|newFileMenuItem
operator|.
name|addActionListener
argument_list|(
operator|new
name|java
operator|.
name|awt
operator|.
name|event
operator|.
name|ActionListener
argument_list|()
block|{
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|newFileMenuItem_actionPerformed
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|pass3aTextPane
operator|.
name|setEditable
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|pass3bTextPane
operator|.
name|setEditable
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|pass3aJList
operator|.
name|addListSelectionListener
argument_list|(
operator|new
name|javax
operator|.
name|swing
operator|.
name|event
operator|.
name|ListSelectionListener
argument_list|()
block|{
specifier|public
name|void
name|valueChanged
parameter_list|(
name|ListSelectionEvent
name|e
parameter_list|)
block|{
name|pass3aJList_valueChanged
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|pass3bJList
operator|.
name|addListSelectionListener
argument_list|(
operator|new
name|javax
operator|.
name|swing
operator|.
name|event
operator|.
name|ListSelectionListener
argument_list|()
block|{
specifier|public
name|void
name|valueChanged
parameter_list|(
name|ListSelectionEvent
name|e
parameter_list|)
block|{
name|pass3bJList_valueChanged
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|jMenu2
operator|.
name|setText
argument_list|(
literal|"Help"
argument_list|)
expr_stmt|;
name|whatisMenuItem
operator|.
name|setText
argument_list|(
literal|"What is..."
argument_list|)
expr_stmt|;
name|whatisMenuItem
operator|.
name|addActionListener
argument_list|(
operator|new
name|java
operator|.
name|awt
operator|.
name|event
operator|.
name|ActionListener
argument_list|()
block|{
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|whatisMenuItem_actionPerformed
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|aboutMenuItem
operator|.
name|setText
argument_list|(
literal|"About"
argument_list|)
expr_stmt|;
name|aboutMenuItem
operator|.
name|addActionListener
argument_list|(
operator|new
name|java
operator|.
name|awt
operator|.
name|event
operator|.
name|ActionListener
argument_list|()
block|{
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|aboutMenuItem_actionPerformed
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|jSplitPane2
operator|.
name|add
argument_list|(
name|messagesPanel
argument_list|,
name|JSplitPane
operator|.
name|BOTTOM
argument_list|)
expr_stmt|;
name|messagesPanel
operator|.
name|add
argument_list|(
name|messagesScrollPane
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|messagesScrollPane
operator|.
name|getViewport
argument_list|()
operator|.
name|add
argument_list|(
name|messagesTextPane
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|jSplitPane2
operator|.
name|add
argument_list|(
name|jPanel3
argument_list|,
name|JSplitPane
operator|.
name|TOP
argument_list|)
expr_stmt|;
name|jPanel3
operator|.
name|add
argument_list|(
name|jScrollPane3
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|jScrollPane3
operator|.
name|getViewport
argument_list|()
operator|.
name|add
argument_list|(
name|pass1TextPane
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|jPanel3
operator|.
name|add
argument_list|(
name|jScrollPane4
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|jPanel3
operator|.
name|add
argument_list|(
name|jSplitPane3
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|jSplitPane3
operator|.
name|add
argument_list|(
name|jScrollPane2
argument_list|,
name|JSplitPane
operator|.
name|LEFT
argument_list|)
expr_stmt|;
name|jScrollPane2
operator|.
name|getViewport
argument_list|()
operator|.
name|add
argument_list|(
name|pass3aJList
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|jSplitPane3
operator|.
name|add
argument_list|(
name|jScrollPane5
argument_list|,
name|JSplitPane
operator|.
name|RIGHT
argument_list|)
expr_stmt|;
name|jScrollPane5
operator|.
name|getViewport
argument_list|()
operator|.
name|add
argument_list|(
name|pass3aTextPane
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|jPanel3
operator|.
name|add
argument_list|(
name|jSplitPane4
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|jSplitPane4
operator|.
name|add
argument_list|(
name|jScrollPane6
argument_list|,
name|JSplitPane
operator|.
name|LEFT
argument_list|)
expr_stmt|;
name|jScrollPane6
operator|.
name|getViewport
argument_list|()
operator|.
name|add
argument_list|(
name|pass3bJList
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|jSplitPane4
operator|.
name|add
argument_list|(
name|jScrollPane7
argument_list|,
name|JSplitPane
operator|.
name|RIGHT
argument_list|)
expr_stmt|;
name|jScrollPane7
operator|.
name|getViewport
argument_list|()
operator|.
name|add
argument_list|(
name|pass3bTextPane
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|jScrollPane4
operator|.
name|getViewport
argument_list|()
operator|.
name|add
argument_list|(
name|pass2TextPane
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|jSplitPane1
operator|.
name|add
argument_list|(
name|jPanel2
argument_list|,
name|JSplitPane
operator|.
name|TOP
argument_list|)
expr_stmt|;
name|jPanel2
operator|.
name|add
argument_list|(
name|jScrollPane1
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|jSplitPane1
operator|.
name|add
argument_list|(
name|jPanel1
argument_list|,
name|JSplitPane
operator|.
name|BOTTOM
argument_list|)
expr_stmt|;
name|jPanel1
operator|.
name|add
argument_list|(
name|jSplitPane2
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|jScrollPane1
operator|.
name|getViewport
argument_list|()
operator|.
name|add
argument_list|(
name|classNamesJList
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|jMenuBar1
operator|.
name|add
argument_list|(
name|jMenu1
argument_list|)
expr_stmt|;
name|jMenuBar1
operator|.
name|add
argument_list|(
name|jMenu2
argument_list|)
expr_stmt|;
name|contentPane
operator|.
name|add
argument_list|(
name|jSplitPane1
argument_list|,
literal|"jSplitPane1"
argument_list|)
expr_stmt|;
name|jMenu1
operator|.
name|add
argument_list|(
name|newFileMenuItem
argument_list|)
expr_stmt|;
name|jMenu2
operator|.
name|add
argument_list|(
name|whatisMenuItem
argument_list|)
expr_stmt|;
name|jMenu2
operator|.
name|add
argument_list|(
name|aboutMenuItem
argument_list|)
expr_stmt|;
name|jSplitPane2
operator|.
name|setDividerLocation
argument_list|(
literal|300
argument_list|)
expr_stmt|;
name|jSplitPane3
operator|.
name|setDividerLocation
argument_list|(
literal|150
argument_list|)
expr_stmt|;
name|jSplitPane4
operator|.
name|setDividerLocation
argument_list|(
literal|150
argument_list|)
expr_stmt|;
block|}
comment|/** Overridden to stop the application on a closing window. */
specifier|protected
name|void
name|processWindowEvent
parameter_list|(
name|WindowEvent
name|e
parameter_list|)
block|{
name|super
operator|.
name|processWindowEvent
argument_list|(
name|e
argument_list|)
expr_stmt|;
if|if
condition|(
name|e
operator|.
name|getID
argument_list|()
operator|==
name|WindowEvent
operator|.
name|WINDOW_CLOSING
condition|)
block|{
name|System
operator|.
name|exit
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
block|}
specifier|synchronized
name|void
name|classNamesJList_valueChanged
parameter_list|(
name|ListSelectionEvent
name|e
parameter_list|)
block|{
if|if
condition|(
name|e
operator|.
name|getValueIsAdjusting
argument_list|()
condition|)
block|{
return|return;
block|}
name|current_class
operator|=
name|classNamesJList
operator|.
name|getSelectedValue
argument_list|()
operator|.
name|toString
argument_list|()
expr_stmt|;
try|try
block|{
name|verify
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ClassNotFoundException
name|ex
parameter_list|)
block|{
comment|// FIXME: report the error using the GUI
name|ex
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
name|classNamesJList
operator|.
name|setSelectedValue
argument_list|(
name|current_class
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
specifier|private
name|void
name|verify
parameter_list|()
throws|throws
name|ClassNotFoundException
block|{
name|setTitle
argument_list|(
literal|"PLEASE WAIT"
argument_list|)
expr_stmt|;
name|Verifier
name|v
init|=
name|VerifierFactory
operator|.
name|getVerifier
argument_list|(
name|current_class
argument_list|)
decl_stmt|;
name|v
operator|.
name|flush
argument_list|()
expr_stmt|;
comment|// Don't cache the verification result for this class.
name|VerificationResult
name|vr
decl_stmt|;
name|vr
operator|=
name|v
operator|.
name|doPass1
argument_list|()
expr_stmt|;
if|if
condition|(
name|vr
operator|.
name|getStatus
argument_list|()
operator|==
name|VerificationResult
operator|.
name|VERIFIED_REJECTED
condition|)
block|{
name|pass1TextPane
operator|.
name|setText
argument_list|(
name|vr
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
name|pass1TextPane
operator|.
name|setBackground
argument_list|(
name|Color
operator|.
name|red
argument_list|)
expr_stmt|;
name|pass2TextPane
operator|.
name|setText
argument_list|(
literal|""
argument_list|)
expr_stmt|;
name|pass2TextPane
operator|.
name|setBackground
argument_list|(
name|Color
operator|.
name|yellow
argument_list|)
expr_stmt|;
name|pass3aTextPane
operator|.
name|setText
argument_list|(
literal|""
argument_list|)
expr_stmt|;
name|pass3aJList
operator|.
name|setListData
argument_list|(
operator|new
name|Object
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
name|pass3aTextPane
operator|.
name|setBackground
argument_list|(
name|Color
operator|.
name|yellow
argument_list|)
expr_stmt|;
name|pass3bTextPane
operator|.
name|setText
argument_list|(
literal|""
argument_list|)
expr_stmt|;
name|pass3bJList
operator|.
name|setListData
argument_list|(
operator|new
name|Object
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
name|pass3bTextPane
operator|.
name|setBackground
argument_list|(
name|Color
operator|.
name|yellow
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// Must be VERIFIED_OK, Pass 1 does not know VERIFIED_NOTYET
name|pass1TextPane
operator|.
name|setBackground
argument_list|(
name|Color
operator|.
name|green
argument_list|)
expr_stmt|;
name|pass1TextPane
operator|.
name|setText
argument_list|(
name|vr
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
name|vr
operator|=
name|v
operator|.
name|doPass2
argument_list|()
expr_stmt|;
if|if
condition|(
name|vr
operator|.
name|getStatus
argument_list|()
operator|==
name|VerificationResult
operator|.
name|VERIFIED_REJECTED
condition|)
block|{
name|pass2TextPane
operator|.
name|setText
argument_list|(
name|vr
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
name|pass2TextPane
operator|.
name|setBackground
argument_list|(
name|Color
operator|.
name|red
argument_list|)
expr_stmt|;
name|pass3aTextPane
operator|.
name|setText
argument_list|(
literal|""
argument_list|)
expr_stmt|;
name|pass3aTextPane
operator|.
name|setBackground
argument_list|(
name|Color
operator|.
name|yellow
argument_list|)
expr_stmt|;
name|pass3aJList
operator|.
name|setListData
argument_list|(
operator|new
name|Object
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
name|pass3bTextPane
operator|.
name|setText
argument_list|(
literal|""
argument_list|)
expr_stmt|;
name|pass3bTextPane
operator|.
name|setBackground
argument_list|(
name|Color
operator|.
name|yellow
argument_list|)
expr_stmt|;
name|pass3bJList
operator|.
name|setListData
argument_list|(
operator|new
name|Object
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// must be Verified_OK, because Pass1 was OK (cannot be Verified_NOTYET).
name|pass2TextPane
operator|.
name|setText
argument_list|(
name|vr
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
name|pass2TextPane
operator|.
name|setBackground
argument_list|(
name|Color
operator|.
name|green
argument_list|)
expr_stmt|;
name|JavaClass
name|jc
init|=
name|Repository
operator|.
name|lookupClass
argument_list|(
name|current_class
argument_list|)
decl_stmt|;
comment|/*                  boolean all3aok = true;                  boolean all3bok = true;                  String all3amsg = "";                  String all3bmsg = "";                  */
name|String
index|[]
name|methodnames
init|=
operator|new
name|String
index|[
name|jc
operator|.
name|getMethods
argument_list|()
operator|.
name|length
index|]
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|jc
operator|.
name|getMethods
argument_list|()
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|methodnames
index|[
name|i
index|]
operator|=
name|jc
operator|.
name|getMethods
argument_list|()
index|[
name|i
index|]
operator|.
name|toString
argument_list|()
operator|.
name|replace
argument_list|(
literal|'\n'
argument_list|,
literal|' '
argument_list|)
operator|.
name|replace
argument_list|(
literal|'\t'
argument_list|,
literal|' '
argument_list|)
expr_stmt|;
block|}
name|pass3aJList
operator|.
name|setListData
argument_list|(
name|methodnames
argument_list|)
expr_stmt|;
name|pass3aJList
operator|.
name|setSelectionInterval
argument_list|(
literal|0
argument_list|,
name|jc
operator|.
name|getMethods
argument_list|()
operator|.
name|length
operator|-
literal|1
argument_list|)
expr_stmt|;
name|pass3bJList
operator|.
name|setListData
argument_list|(
name|methodnames
argument_list|)
expr_stmt|;
name|pass3bJList
operator|.
name|setSelectionInterval
argument_list|(
literal|0
argument_list|,
name|jc
operator|.
name|getMethods
argument_list|()
operator|.
name|length
operator|-
literal|1
argument_list|)
expr_stmt|;
block|}
block|}
name|String
index|[]
name|msgs
init|=
name|v
operator|.
name|getMessages
argument_list|()
decl_stmt|;
name|messagesTextPane
operator|.
name|setBackground
argument_list|(
name|msgs
operator|.
name|length
operator|==
literal|0
condition|?
name|Color
operator|.
name|green
else|:
name|Color
operator|.
name|yellow
argument_list|)
expr_stmt|;
name|String
name|allmsgs
init|=
literal|""
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|msgs
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|msgs
index|[
name|i
index|]
operator|=
name|msgs
index|[
name|i
index|]
operator|.
name|replace
argument_list|(
literal|'\n'
argument_list|,
literal|' '
argument_list|)
expr_stmt|;
name|allmsgs
operator|+=
name|msgs
index|[
name|i
index|]
operator|+
literal|"\n\n"
expr_stmt|;
block|}
name|messagesTextPane
operator|.
name|setText
argument_list|(
name|allmsgs
argument_list|)
expr_stmt|;
name|setTitle
argument_list|(
name|current_class
operator|+
literal|" - "
operator|+
name|JUSTICE_VERSION
argument_list|)
expr_stmt|;
block|}
name|void
name|newFileMenuItem_actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|String
name|classname
init|=
name|JOptionPane
operator|.
name|showInputDialog
argument_list|(
literal|"Please enter the fully qualified name of a class or interface to verify:"
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|classname
operator|==
literal|null
operator|)
operator|||
operator|(
name|classname
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
operator|)
condition|)
block|{
return|return;
block|}
name|VerifierFactory
operator|.
name|getVerifier
argument_list|(
name|classname
argument_list|)
expr_stmt|;
comment|// let observers do the rest.
name|classNamesJList
operator|.
name|setSelectedValue
argument_list|(
name|classname
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
specifier|synchronized
name|void
name|pass3aJList_valueChanged
parameter_list|(
name|ListSelectionEvent
name|e
parameter_list|)
block|{
if|if
condition|(
name|e
operator|.
name|getValueIsAdjusting
argument_list|()
condition|)
block|{
return|return;
block|}
name|Verifier
name|v
init|=
name|VerifierFactory
operator|.
name|getVerifier
argument_list|(
name|current_class
argument_list|)
decl_stmt|;
name|String
name|all3amsg
init|=
literal|""
decl_stmt|;
name|boolean
name|all3aok
init|=
literal|true
decl_stmt|;
name|boolean
name|rejected
init|=
literal|false
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|pass3aJList
operator|.
name|getModel
argument_list|()
operator|.
name|getSize
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|pass3aJList
operator|.
name|isSelectedIndex
argument_list|(
name|i
argument_list|)
condition|)
block|{
name|VerificationResult
name|vr
init|=
name|v
operator|.
name|doPass3a
argument_list|(
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
name|vr
operator|.
name|getStatus
argument_list|()
operator|==
name|VerificationResult
operator|.
name|VERIFIED_REJECTED
condition|)
block|{
name|all3aok
operator|=
literal|false
expr_stmt|;
name|rejected
operator|=
literal|true
expr_stmt|;
block|}
name|JavaClass
name|jc
init|=
literal|null
decl_stmt|;
try|try
block|{
name|jc
operator|=
name|Repository
operator|.
name|lookupClass
argument_list|(
name|v
operator|.
name|getClassName
argument_list|()
argument_list|)
expr_stmt|;
name|all3amsg
operator|+=
literal|"Method '"
operator|+
name|jc
operator|.
name|getMethods
argument_list|()
index|[
name|i
index|]
operator|+
literal|"': "
operator|+
name|vr
operator|.
name|getMessage
argument_list|()
operator|.
name|replace
argument_list|(
literal|'\n'
argument_list|,
literal|' '
argument_list|)
operator|+
literal|"\n\n"
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ClassNotFoundException
name|ex
parameter_list|)
block|{
comment|// FIXME: handle the error
name|ex
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
block|}
name|pass3aTextPane
operator|.
name|setText
argument_list|(
name|all3amsg
argument_list|)
expr_stmt|;
name|pass3aTextPane
operator|.
name|setBackground
argument_list|(
name|all3aok
condition|?
name|Color
operator|.
name|green
else|:
operator|(
name|rejected
condition|?
name|Color
operator|.
name|red
else|:
name|Color
operator|.
name|yellow
operator|)
argument_list|)
expr_stmt|;
block|}
specifier|synchronized
name|void
name|pass3bJList_valueChanged
parameter_list|(
name|ListSelectionEvent
name|e
parameter_list|)
block|{
if|if
condition|(
name|e
operator|.
name|getValueIsAdjusting
argument_list|()
condition|)
block|{
return|return;
block|}
name|Verifier
name|v
init|=
name|VerifierFactory
operator|.
name|getVerifier
argument_list|(
name|current_class
argument_list|)
decl_stmt|;
name|String
name|all3bmsg
init|=
literal|""
decl_stmt|;
name|boolean
name|all3bok
init|=
literal|true
decl_stmt|;
name|boolean
name|rejected
init|=
literal|false
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|pass3bJList
operator|.
name|getModel
argument_list|()
operator|.
name|getSize
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|pass3bJList
operator|.
name|isSelectedIndex
argument_list|(
name|i
argument_list|)
condition|)
block|{
name|VerificationResult
name|vr
init|=
name|v
operator|.
name|doPass3b
argument_list|(
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
name|vr
operator|.
name|getStatus
argument_list|()
operator|==
name|VerificationResult
operator|.
name|VERIFIED_REJECTED
condition|)
block|{
name|all3bok
operator|=
literal|false
expr_stmt|;
name|rejected
operator|=
literal|true
expr_stmt|;
block|}
name|JavaClass
name|jc
init|=
literal|null
decl_stmt|;
try|try
block|{
name|jc
operator|=
name|Repository
operator|.
name|lookupClass
argument_list|(
name|v
operator|.
name|getClassName
argument_list|()
argument_list|)
expr_stmt|;
name|all3bmsg
operator|+=
literal|"Method '"
operator|+
name|jc
operator|.
name|getMethods
argument_list|()
index|[
name|i
index|]
operator|+
literal|"': "
operator|+
name|vr
operator|.
name|getMessage
argument_list|()
operator|.
name|replace
argument_list|(
literal|'\n'
argument_list|,
literal|' '
argument_list|)
operator|+
literal|"\n\n"
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ClassNotFoundException
name|ex
parameter_list|)
block|{
comment|// FIXME: handle the error
name|ex
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
block|}
name|pass3bTextPane
operator|.
name|setText
argument_list|(
name|all3bmsg
argument_list|)
expr_stmt|;
name|pass3bTextPane
operator|.
name|setBackground
argument_list|(
name|all3bok
condition|?
name|Color
operator|.
name|green
else|:
operator|(
name|rejected
condition|?
name|Color
operator|.
name|red
else|:
name|Color
operator|.
name|yellow
operator|)
argument_list|)
expr_stmt|;
block|}
name|void
name|aboutMenuItem_actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|this
argument_list|,
literal|"JustIce is a Java class file verifier.\nIt was implemented by Enver Haase in 2001, 2002.\n<http://jakarta.apache.org/bcel/index.html>"
argument_list|,
name|JUSTICE_VERSION
argument_list|,
name|JOptionPane
operator|.
name|INFORMATION_MESSAGE
argument_list|)
expr_stmt|;
block|}
name|void
name|whatisMenuItem_actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|this
argument_list|,
literal|"The upper four boxes to the right reflect verification passes according to The Java Virtual Machine Specification.\nThese are (in that order): Pass one, Pass two, Pass three (before data flow analysis), Pass three (data flow analysis).\nThe bottom box to the right shows (warning) messages; warnings do not cause a class to be rejected."
argument_list|,
name|JUSTICE_VERSION
argument_list|,
name|JOptionPane
operator|.
name|INFORMATION_MESSAGE
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

