begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreements.  See the NOTICE file distributed with  * this work for additional information regarding copyright ownership.  * The ASF licenses this file to You under the Apache License, Version 2.0  * (the "License"); you may not use this file except in compliance with  * the License.  You may obtain a copy of the License at  *  *      http://www.apache.org/licenses/LICENSE-2.0  *  *  Unless required by applicable law or agreed to in writing, software  *  distributed under the License is distributed on an "AS IS" BASIS,  *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *  See the License for the specific language governing permissions and  *  limitations under the License.  */
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
name|Color
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Dialog
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Frame
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|SystemColor
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
name|ActionListener
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
name|WindowAdapter
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
name|JButton
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JDialog
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
name|WindowConstants
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
name|Utility
import|;
end_import

begin_comment
comment|/**  * A class for simple graphical class file verification. Use the main(String []) method with fully qualified class names  * as arguments to use it as a stand-alone application. Use the VerifyDialog(String) constructor to use this class in  * your application. [This class was created using VisualAge for Java, but it does not work under VAJ itself (Version  * 3.02 JDK 1.2)]  *  * @see #main(String[])  * @see #VerifyDialog(String)  */
end_comment

begin_class
specifier|public
class|class
name|VerifyDialog
extends|extends
name|JDialog
block|{
comment|/** Machine-generated. */
class|class
name|IvjEventHandler
implements|implements
name|ActionListener
block|{
annotation|@
name|Override
specifier|public
name|void
name|actionPerformed
parameter_list|(
specifier|final
name|ActionEvent
name|e
parameter_list|)
block|{
if|if
condition|(
name|e
operator|.
name|getSource
argument_list|()
operator|==
name|VerifyDialog
operator|.
name|this
operator|.
name|getPass1Button
argument_list|()
condition|)
block|{
name|connEtoC1
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|e
operator|.
name|getSource
argument_list|()
operator|==
name|VerifyDialog
operator|.
name|this
operator|.
name|getPass2Button
argument_list|()
condition|)
block|{
name|connEtoC2
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|e
operator|.
name|getSource
argument_list|()
operator|==
name|VerifyDialog
operator|.
name|this
operator|.
name|getPass3Button
argument_list|()
condition|)
block|{
name|connEtoC3
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|e
operator|.
name|getSource
argument_list|()
operator|==
name|VerifyDialog
operator|.
name|this
operator|.
name|getFlushButton
argument_list|()
condition|)
block|{
name|connEtoC4
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
block|}
block|}
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
operator|-
literal|6374807677043142313L
decl_stmt|;
comment|/**      * This field is here to count the number of open VerifyDialog instances so the JVM can be exited afer every Dialog had      * been closed.      */
specifier|private
specifier|static
name|int
name|classesToVerify
decl_stmt|;
comment|/**      * Verifies one or more class files. Verification results are presented graphically: Red means 'rejected', green means      * 'passed' while yellow means 'could not be verified yet'.      *      * @param args String[] fully qualified names of classes to verify.      */
specifier|public
specifier|static
name|void
name|main
parameter_list|(
specifier|final
name|String
index|[]
name|args
parameter_list|)
block|{
name|classesToVerify
operator|=
name|args
operator|.
name|length
expr_stmt|;
for|for
control|(
specifier|final
name|String
name|arg
range|:
name|args
control|)
block|{
try|try
block|{
specifier|final
name|VerifyDialog
name|aVerifyDialog
decl_stmt|;
name|aVerifyDialog
operator|=
operator|new
name|VerifyDialog
argument_list|(
name|arg
argument_list|)
expr_stmt|;
name|aVerifyDialog
operator|.
name|setModal
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|aVerifyDialog
operator|.
name|addWindowListener
argument_list|(
operator|new
name|WindowAdapter
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|windowClosing
parameter_list|(
specifier|final
name|WindowEvent
name|e
parameter_list|)
block|{
name|classesToVerify
operator|--
expr_stmt|;
if|if
condition|(
name|classesToVerify
operator|==
literal|0
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
block|}
argument_list|)
expr_stmt|;
name|aVerifyDialog
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
specifier|final
name|Throwable
name|exception
parameter_list|)
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"Exception occurred in main() of JDialog"
argument_list|)
expr_stmt|;
name|exception
operator|.
name|printStackTrace
argument_list|(
name|System
operator|.
name|out
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|/** Machine-generated. */
specifier|private
name|JPanel
name|ivjJDialogContentPane
decl_stmt|;
comment|/** Machine-generated. */
specifier|private
name|JPanel
name|ivjPass1Panel
decl_stmt|;
comment|/** Machine-generated. */
specifier|private
name|JPanel
name|ivjPass2Panel
decl_stmt|;
comment|/** Machine-generated. */
specifier|private
name|JPanel
name|ivjPass3Panel
decl_stmt|;
comment|/** Machine-generated. */
specifier|private
name|JButton
name|ivjPass1Button
decl_stmt|;
comment|/** Machine-generated. */
specifier|private
name|JButton
name|ivjPass2Button
decl_stmt|;
comment|/** Machine-generated. */
specifier|private
name|JButton
name|ivjPass3Button
decl_stmt|;
comment|/** Machine-generated. */
specifier|private
specifier|final
name|IvjEventHandler
name|ivjEventHandler
init|=
operator|new
name|IvjEventHandler
argument_list|()
decl_stmt|;
comment|/**      * The class to verify. Default set to 'java.lang.Object' in case this class is instantiated via one of the many      * machine-generated constructors.      */
specifier|private
name|String
name|className
init|=
literal|"java.lang.Object"
decl_stmt|;
comment|/** Machine-generated. */
specifier|private
name|JButton
name|ivjFlushButton
decl_stmt|;
comment|/** Machine-generated. */
specifier|public
name|VerifyDialog
parameter_list|()
block|{
name|initialize
argument_list|()
expr_stmt|;
block|}
comment|/** Machine-generated. */
specifier|public
name|VerifyDialog
parameter_list|(
specifier|final
name|Dialog
name|owner
parameter_list|)
block|{
name|super
argument_list|(
name|owner
argument_list|)
expr_stmt|;
block|}
comment|/** Machine-generated. */
specifier|public
name|VerifyDialog
parameter_list|(
specifier|final
name|Dialog
name|owner
parameter_list|,
specifier|final
name|boolean
name|modal
parameter_list|)
block|{
name|super
argument_list|(
name|owner
argument_list|,
name|modal
argument_list|)
expr_stmt|;
block|}
comment|/** Machine-generated. */
specifier|public
name|VerifyDialog
parameter_list|(
specifier|final
name|Dialog
name|owner
parameter_list|,
specifier|final
name|String
name|title
parameter_list|)
block|{
name|super
argument_list|(
name|owner
argument_list|,
name|title
argument_list|)
expr_stmt|;
block|}
comment|/** Machine-generated. */
specifier|public
name|VerifyDialog
parameter_list|(
specifier|final
name|Dialog
name|owner
parameter_list|,
specifier|final
name|String
name|title
parameter_list|,
specifier|final
name|boolean
name|modal
parameter_list|)
block|{
name|super
argument_list|(
name|owner
argument_list|,
name|title
argument_list|,
name|modal
argument_list|)
expr_stmt|;
block|}
comment|/** Machine-generated. */
specifier|public
name|VerifyDialog
parameter_list|(
specifier|final
name|Frame
name|owner
parameter_list|)
block|{
name|super
argument_list|(
name|owner
argument_list|)
expr_stmt|;
block|}
comment|/** Machine-generated. */
specifier|public
name|VerifyDialog
parameter_list|(
specifier|final
name|Frame
name|owner
parameter_list|,
specifier|final
name|boolean
name|modal
parameter_list|)
block|{
name|super
argument_list|(
name|owner
argument_list|,
name|modal
argument_list|)
expr_stmt|;
block|}
comment|/** Machine-generated. */
specifier|public
name|VerifyDialog
parameter_list|(
specifier|final
name|Frame
name|owner
parameter_list|,
specifier|final
name|String
name|title
parameter_list|)
block|{
name|super
argument_list|(
name|owner
argument_list|,
name|title
argument_list|)
expr_stmt|;
block|}
comment|/** Machine-generated. */
specifier|public
name|VerifyDialog
parameter_list|(
specifier|final
name|Frame
name|owner
parameter_list|,
specifier|final
name|String
name|title
parameter_list|,
specifier|final
name|boolean
name|modal
parameter_list|)
block|{
name|super
argument_list|(
name|owner
argument_list|,
name|title
argument_list|,
name|modal
argument_list|)
expr_stmt|;
block|}
comment|/**      * Use this constructor if you want a possibility to verify other class files than java.lang.Object.      *      * @param fullyQualifiedClassName java.lang.String      */
specifier|public
name|VerifyDialog
parameter_list|(
name|String
name|fullyQualifiedClassName
parameter_list|)
block|{
specifier|final
name|int
name|dotclasspos
init|=
name|fullyQualifiedClassName
operator|.
name|lastIndexOf
argument_list|(
name|JavaClass
operator|.
name|EXTENSION
argument_list|)
decl_stmt|;
if|if
condition|(
name|dotclasspos
operator|!=
operator|-
literal|1
condition|)
block|{
name|fullyQualifiedClassName
operator|=
name|fullyQualifiedClassName
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|dotclasspos
argument_list|)
expr_stmt|;
block|}
name|fullyQualifiedClassName
operator|=
name|Utility
operator|.
name|pathToPackage
argument_list|(
name|fullyQualifiedClassName
argument_list|)
expr_stmt|;
name|this
operator|.
name|className
operator|=
name|fullyQualifiedClassName
expr_stmt|;
name|initialize
argument_list|()
expr_stmt|;
block|}
comment|/** Machine-generated. */
specifier|private
name|void
name|connEtoC1
parameter_list|(
specifier|final
name|ActionEvent
name|arg1
parameter_list|)
block|{
try|try
block|{
comment|// user code begin {1}
comment|// user code end
name|this
operator|.
name|pass1Button_ActionPerformed
argument_list|(
name|arg1
argument_list|)
expr_stmt|;
comment|// user code begin {2}
comment|// user code end
block|}
catch|catch
parameter_list|(
specifier|final
name|Throwable
name|ivjExc
parameter_list|)
block|{
comment|// user code begin {3}
comment|// user code end
name|handleException
argument_list|(
name|ivjExc
argument_list|)
expr_stmt|;
block|}
block|}
comment|/** Machine-generated. */
specifier|private
name|void
name|connEtoC2
parameter_list|(
specifier|final
name|ActionEvent
name|arg1
parameter_list|)
block|{
try|try
block|{
comment|// user code begin {1}
comment|// user code end
name|this
operator|.
name|pass2Button_ActionPerformed
argument_list|(
name|arg1
argument_list|)
expr_stmt|;
comment|// user code begin {2}
comment|// user code end
block|}
catch|catch
parameter_list|(
specifier|final
name|Throwable
name|ivjExc
parameter_list|)
block|{
comment|// user code begin {3}
comment|// user code end
name|handleException
argument_list|(
name|ivjExc
argument_list|)
expr_stmt|;
block|}
block|}
comment|/** Machine-generated. */
specifier|private
name|void
name|connEtoC3
parameter_list|(
specifier|final
name|ActionEvent
name|arg1
parameter_list|)
block|{
try|try
block|{
comment|// user code begin {1}
comment|// user code end
name|this
operator|.
name|pass4Button_ActionPerformed
argument_list|(
name|arg1
argument_list|)
expr_stmt|;
comment|// user code begin {2}
comment|// user code end
block|}
catch|catch
parameter_list|(
specifier|final
name|Throwable
name|ivjExc
parameter_list|)
block|{
comment|// user code begin {3}
comment|// user code end
name|handleException
argument_list|(
name|ivjExc
argument_list|)
expr_stmt|;
block|}
block|}
comment|/** Machine-generated. */
specifier|private
name|void
name|connEtoC4
parameter_list|(
specifier|final
name|ActionEvent
name|arg1
parameter_list|)
block|{
try|try
block|{
comment|// user code begin {1}
comment|// user code end
name|this
operator|.
name|flushButton_ActionPerformed
argument_list|(
name|arg1
argument_list|)
expr_stmt|;
comment|// user code begin {2}
comment|// user code end
block|}
catch|catch
parameter_list|(
specifier|final
name|Throwable
name|ivjExc
parameter_list|)
block|{
comment|// user code begin {3}
comment|// user code end
name|handleException
argument_list|(
name|ivjExc
argument_list|)
expr_stmt|;
block|}
block|}
comment|/** Machine-generated. */
specifier|public
name|void
name|flushButton_ActionPerformed
parameter_list|(
specifier|final
name|ActionEvent
name|actionEvent
parameter_list|)
block|{
name|VerifierFactory
operator|.
name|getVerifier
argument_list|(
name|className
argument_list|)
operator|.
name|flush
argument_list|()
expr_stmt|;
name|Repository
operator|.
name|removeClass
argument_list|(
name|className
argument_list|)
expr_stmt|;
comment|// Make sure it will be reloaded.
name|getPass1Panel
argument_list|()
operator|.
name|setBackground
argument_list|(
name|Color
operator|.
name|gray
argument_list|)
expr_stmt|;
name|getPass1Panel
argument_list|()
operator|.
name|repaint
argument_list|()
expr_stmt|;
name|getPass2Panel
argument_list|()
operator|.
name|setBackground
argument_list|(
name|Color
operator|.
name|gray
argument_list|)
expr_stmt|;
name|getPass2Panel
argument_list|()
operator|.
name|repaint
argument_list|()
expr_stmt|;
name|getPass3Panel
argument_list|()
operator|.
name|setBackground
argument_list|(
name|Color
operator|.
name|gray
argument_list|)
expr_stmt|;
name|getPass3Panel
argument_list|()
operator|.
name|repaint
argument_list|()
expr_stmt|;
block|}
comment|/** Machine-generated. */
specifier|private
name|JButton
name|getFlushButton
parameter_list|()
block|{
if|if
condition|(
name|ivjFlushButton
operator|==
literal|null
condition|)
block|{
try|try
block|{
name|ivjFlushButton
operator|=
operator|new
name|JButton
argument_list|()
expr_stmt|;
name|ivjFlushButton
operator|.
name|setName
argument_list|(
literal|"FlushButton"
argument_list|)
expr_stmt|;
name|ivjFlushButton
operator|.
name|setText
argument_list|(
literal|"Flush: Forget old verification results"
argument_list|)
expr_stmt|;
name|ivjFlushButton
operator|.
name|setBackground
argument_list|(
name|SystemColor
operator|.
name|controlHighlight
argument_list|)
expr_stmt|;
name|ivjFlushButton
operator|.
name|setBounds
argument_list|(
literal|60
argument_list|,
literal|215
argument_list|,
literal|300
argument_list|,
literal|30
argument_list|)
expr_stmt|;
name|ivjFlushButton
operator|.
name|setForeground
argument_list|(
name|Color
operator|.
name|red
argument_list|)
expr_stmt|;
name|ivjFlushButton
operator|.
name|setActionCommand
argument_list|(
literal|"FlushButton"
argument_list|)
expr_stmt|;
comment|// user code begin {1}
comment|// user code end
block|}
catch|catch
parameter_list|(
specifier|final
name|Throwable
name|ivjExc
parameter_list|)
block|{
comment|// user code begin {2}
comment|// user code end
name|handleException
argument_list|(
name|ivjExc
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|ivjFlushButton
return|;
block|}
comment|/** Machine-generated. */
specifier|private
name|JPanel
name|getJDialogContentPane
parameter_list|()
block|{
if|if
condition|(
name|ivjJDialogContentPane
operator|==
literal|null
condition|)
block|{
try|try
block|{
name|ivjJDialogContentPane
operator|=
operator|new
name|JPanel
argument_list|()
expr_stmt|;
name|ivjJDialogContentPane
operator|.
name|setName
argument_list|(
literal|"JDialogContentPane"
argument_list|)
expr_stmt|;
name|ivjJDialogContentPane
operator|.
name|setLayout
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|getJDialogContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|getPass1Panel
argument_list|()
argument_list|,
name|getPass1Panel
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|getJDialogContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|getPass3Panel
argument_list|()
argument_list|,
name|getPass3Panel
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|getJDialogContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|getPass2Panel
argument_list|()
argument_list|,
name|getPass2Panel
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|getJDialogContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|getPass1Button
argument_list|()
argument_list|,
name|getPass1Button
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|getJDialogContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|getPass2Button
argument_list|()
argument_list|,
name|getPass2Button
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|getJDialogContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|getPass3Button
argument_list|()
argument_list|,
name|getPass3Button
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|getJDialogContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|getFlushButton
argument_list|()
argument_list|,
name|getFlushButton
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
comment|// user code begin {1}
comment|// user code end
block|}
catch|catch
parameter_list|(
specifier|final
name|Throwable
name|ivjExc
parameter_list|)
block|{
comment|// user code begin {2}
comment|// user code end
name|handleException
argument_list|(
name|ivjExc
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|ivjJDialogContentPane
return|;
block|}
comment|/** Machine-generated. */
specifier|private
name|JButton
name|getPass1Button
parameter_list|()
block|{
if|if
condition|(
name|ivjPass1Button
operator|==
literal|null
condition|)
block|{
try|try
block|{
name|ivjPass1Button
operator|=
operator|new
name|JButton
argument_list|()
expr_stmt|;
name|ivjPass1Button
operator|.
name|setName
argument_list|(
literal|"Pass1Button"
argument_list|)
expr_stmt|;
name|ivjPass1Button
operator|.
name|setText
argument_list|(
literal|"Pass1: Verify binary layout of .class file"
argument_list|)
expr_stmt|;
name|ivjPass1Button
operator|.
name|setBackground
argument_list|(
name|SystemColor
operator|.
name|controlHighlight
argument_list|)
expr_stmt|;
name|ivjPass1Button
operator|.
name|setBounds
argument_list|(
literal|100
argument_list|,
literal|40
argument_list|,
literal|300
argument_list|,
literal|30
argument_list|)
expr_stmt|;
name|ivjPass1Button
operator|.
name|setActionCommand
argument_list|(
literal|"Button1"
argument_list|)
expr_stmt|;
comment|// user code begin {1}
comment|// user code end
block|}
catch|catch
parameter_list|(
specifier|final
name|Throwable
name|ivjExc
parameter_list|)
block|{
comment|// user code begin {2}
comment|// user code end
name|handleException
argument_list|(
name|ivjExc
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|ivjPass1Button
return|;
block|}
comment|/** Machine-generated. */
specifier|private
name|JPanel
name|getPass1Panel
parameter_list|()
block|{
if|if
condition|(
name|ivjPass1Panel
operator|==
literal|null
condition|)
block|{
try|try
block|{
name|ivjPass1Panel
operator|=
operator|new
name|JPanel
argument_list|()
expr_stmt|;
name|ivjPass1Panel
operator|.
name|setName
argument_list|(
literal|"Pass1Panel"
argument_list|)
expr_stmt|;
name|ivjPass1Panel
operator|.
name|setLayout
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|ivjPass1Panel
operator|.
name|setBackground
argument_list|(
name|SystemColor
operator|.
name|controlShadow
argument_list|)
expr_stmt|;
name|ivjPass1Panel
operator|.
name|setBounds
argument_list|(
literal|30
argument_list|,
literal|30
argument_list|,
literal|50
argument_list|,
literal|50
argument_list|)
expr_stmt|;
comment|// user code begin {1}
comment|// user code end
block|}
catch|catch
parameter_list|(
specifier|final
name|Throwable
name|ivjExc
parameter_list|)
block|{
comment|// user code begin {2}
comment|// user code end
name|handleException
argument_list|(
name|ivjExc
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|ivjPass1Panel
return|;
block|}
comment|/** Machine-generated. */
specifier|private
name|JButton
name|getPass2Button
parameter_list|()
block|{
if|if
condition|(
name|ivjPass2Button
operator|==
literal|null
condition|)
block|{
try|try
block|{
name|ivjPass2Button
operator|=
operator|new
name|JButton
argument_list|()
expr_stmt|;
name|ivjPass2Button
operator|.
name|setName
argument_list|(
literal|"Pass2Button"
argument_list|)
expr_stmt|;
name|ivjPass2Button
operator|.
name|setText
argument_list|(
literal|"Pass 2: Verify static .class file constraints"
argument_list|)
expr_stmt|;
name|ivjPass2Button
operator|.
name|setBackground
argument_list|(
name|SystemColor
operator|.
name|controlHighlight
argument_list|)
expr_stmt|;
name|ivjPass2Button
operator|.
name|setBounds
argument_list|(
literal|100
argument_list|,
literal|100
argument_list|,
literal|300
argument_list|,
literal|30
argument_list|)
expr_stmt|;
name|ivjPass2Button
operator|.
name|setActionCommand
argument_list|(
literal|"Button2"
argument_list|)
expr_stmt|;
comment|// user code begin {1}
comment|// user code end
block|}
catch|catch
parameter_list|(
specifier|final
name|Throwable
name|ivjExc
parameter_list|)
block|{
comment|// user code begin {2}
comment|// user code end
name|handleException
argument_list|(
name|ivjExc
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|ivjPass2Button
return|;
block|}
comment|/** Machine-generated. */
specifier|private
name|JPanel
name|getPass2Panel
parameter_list|()
block|{
if|if
condition|(
name|ivjPass2Panel
operator|==
literal|null
condition|)
block|{
try|try
block|{
name|ivjPass2Panel
operator|=
operator|new
name|JPanel
argument_list|()
expr_stmt|;
name|ivjPass2Panel
operator|.
name|setName
argument_list|(
literal|"Pass2Panel"
argument_list|)
expr_stmt|;
name|ivjPass2Panel
operator|.
name|setLayout
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|ivjPass2Panel
operator|.
name|setBackground
argument_list|(
name|SystemColor
operator|.
name|controlShadow
argument_list|)
expr_stmt|;
name|ivjPass2Panel
operator|.
name|setBounds
argument_list|(
literal|30
argument_list|,
literal|90
argument_list|,
literal|50
argument_list|,
literal|50
argument_list|)
expr_stmt|;
comment|// user code begin {1}
comment|// user code end
block|}
catch|catch
parameter_list|(
specifier|final
name|Throwable
name|ivjExc
parameter_list|)
block|{
comment|// user code begin {2}
comment|// user code end
name|handleException
argument_list|(
name|ivjExc
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|ivjPass2Panel
return|;
block|}
comment|/** Machine-generated. */
specifier|private
name|JButton
name|getPass3Button
parameter_list|()
block|{
if|if
condition|(
name|ivjPass3Button
operator|==
literal|null
condition|)
block|{
try|try
block|{
name|ivjPass3Button
operator|=
operator|new
name|JButton
argument_list|()
expr_stmt|;
name|ivjPass3Button
operator|.
name|setName
argument_list|(
literal|"Pass3Button"
argument_list|)
expr_stmt|;
name|ivjPass3Button
operator|.
name|setText
argument_list|(
literal|"Passes 3a+3b: Verify code arrays"
argument_list|)
expr_stmt|;
name|ivjPass3Button
operator|.
name|setBackground
argument_list|(
name|SystemColor
operator|.
name|controlHighlight
argument_list|)
expr_stmt|;
name|ivjPass3Button
operator|.
name|setBounds
argument_list|(
literal|100
argument_list|,
literal|160
argument_list|,
literal|300
argument_list|,
literal|30
argument_list|)
expr_stmt|;
name|ivjPass3Button
operator|.
name|setActionCommand
argument_list|(
literal|"Button2"
argument_list|)
expr_stmt|;
comment|// user code begin {1}
comment|// user code end
block|}
catch|catch
parameter_list|(
specifier|final
name|Throwable
name|ivjExc
parameter_list|)
block|{
comment|// user code begin {2}
comment|// user code end
name|handleException
argument_list|(
name|ivjExc
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|ivjPass3Button
return|;
block|}
comment|/** Machine-generated. */
specifier|private
name|JPanel
name|getPass3Panel
parameter_list|()
block|{
if|if
condition|(
name|ivjPass3Panel
operator|==
literal|null
condition|)
block|{
try|try
block|{
name|ivjPass3Panel
operator|=
operator|new
name|JPanel
argument_list|()
expr_stmt|;
name|ivjPass3Panel
operator|.
name|setName
argument_list|(
literal|"Pass3Panel"
argument_list|)
expr_stmt|;
name|ivjPass3Panel
operator|.
name|setLayout
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|ivjPass3Panel
operator|.
name|setBackground
argument_list|(
name|SystemColor
operator|.
name|controlShadow
argument_list|)
expr_stmt|;
name|ivjPass3Panel
operator|.
name|setBounds
argument_list|(
literal|30
argument_list|,
literal|150
argument_list|,
literal|50
argument_list|,
literal|50
argument_list|)
expr_stmt|;
comment|// user code begin {1}
comment|// user code end
block|}
catch|catch
parameter_list|(
specifier|final
name|Throwable
name|ivjExc
parameter_list|)
block|{
comment|// user code begin {2}
comment|// user code end
name|handleException
argument_list|(
name|ivjExc
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|ivjPass3Panel
return|;
block|}
comment|/** Machine-generated. */
specifier|private
name|void
name|handleException
parameter_list|(
specifier|final
name|Throwable
name|exception
parameter_list|)
block|{
comment|/* Uncomment the following lines to print uncaught exceptions to stdout */
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"--------- UNCAUGHT EXCEPTION ---------"
argument_list|)
expr_stmt|;
name|exception
operator|.
name|printStackTrace
argument_list|(
name|System
operator|.
name|out
argument_list|)
expr_stmt|;
comment|// manually added code
if|if
condition|(
name|exception
operator|instanceof
name|ThreadDeath
condition|)
block|{
throw|throw
operator|(
name|ThreadDeath
operator|)
name|exception
throw|;
block|}
if|if
condition|(
name|exception
operator|instanceof
name|VirtualMachineError
condition|)
block|{
throw|throw
operator|(
name|VirtualMachineError
operator|)
name|exception
throw|;
block|}
block|}
comment|/** Machine-generated. */
specifier|private
name|void
name|initConnections
parameter_list|()
block|{
comment|// user code begin {1}
comment|// user code end
name|getPass1Button
argument_list|()
operator|.
name|addActionListener
argument_list|(
name|ivjEventHandler
argument_list|)
expr_stmt|;
name|getPass2Button
argument_list|()
operator|.
name|addActionListener
argument_list|(
name|ivjEventHandler
argument_list|)
expr_stmt|;
name|getPass3Button
argument_list|()
operator|.
name|addActionListener
argument_list|(
name|ivjEventHandler
argument_list|)
expr_stmt|;
name|getFlushButton
argument_list|()
operator|.
name|addActionListener
argument_list|(
name|ivjEventHandler
argument_list|)
expr_stmt|;
block|}
comment|/** Machine-generated. */
specifier|private
name|void
name|initialize
parameter_list|()
block|{
try|try
block|{
comment|// user code begin {1}
comment|// user code end
name|setName
argument_list|(
literal|"VerifyDialog"
argument_list|)
expr_stmt|;
name|setDefaultCloseOperation
argument_list|(
name|WindowConstants
operator|.
name|DISPOSE_ON_CLOSE
argument_list|)
expr_stmt|;
name|setSize
argument_list|(
literal|430
argument_list|,
literal|280
argument_list|)
expr_stmt|;
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|setModal
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|setResizable
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|setContentPane
argument_list|(
name|getJDialogContentPane
argument_list|()
argument_list|)
expr_stmt|;
name|initConnections
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
specifier|final
name|Throwable
name|ivjExc
parameter_list|)
block|{
name|handleException
argument_list|(
name|ivjExc
argument_list|)
expr_stmt|;
block|}
comment|// user code begin {2}
name|setTitle
argument_list|(
literal|"'"
operator|+
name|className
operator|+
literal|"' verification - JustIce / BCEL"
argument_list|)
expr_stmt|;
comment|// user code end
block|}
comment|/** Machine-generated. */
specifier|public
name|void
name|pass1Button_ActionPerformed
parameter_list|(
specifier|final
name|ActionEvent
name|actionEvent
parameter_list|)
block|{
specifier|final
name|Verifier
name|v
init|=
name|VerifierFactory
operator|.
name|getVerifier
argument_list|(
name|className
argument_list|)
decl_stmt|;
specifier|final
name|VerificationResult
name|vr
init|=
name|v
operator|.
name|doPass1
argument_list|()
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
name|VERIFIED_OK
condition|)
block|{
name|getPass1Panel
argument_list|()
operator|.
name|setBackground
argument_list|(
name|Color
operator|.
name|green
argument_list|)
expr_stmt|;
name|getPass1Panel
argument_list|()
operator|.
name|repaint
argument_list|()
expr_stmt|;
block|}
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
name|getPass1Panel
argument_list|()
operator|.
name|setBackground
argument_list|(
name|Color
operator|.
name|red
argument_list|)
expr_stmt|;
name|getPass1Panel
argument_list|()
operator|.
name|repaint
argument_list|()
expr_stmt|;
block|}
block|}
comment|/** Machine-generated. */
specifier|public
name|void
name|pass2Button_ActionPerformed
parameter_list|(
specifier|final
name|ActionEvent
name|actionEvent
parameter_list|)
block|{
name|pass1Button_ActionPerformed
argument_list|(
name|actionEvent
argument_list|)
expr_stmt|;
specifier|final
name|Verifier
name|v
init|=
name|VerifierFactory
operator|.
name|getVerifier
argument_list|(
name|className
argument_list|)
decl_stmt|;
specifier|final
name|VerificationResult
name|vr
init|=
name|v
operator|.
name|doPass2
argument_list|()
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
name|VERIFIED_OK
condition|)
block|{
name|getPass2Panel
argument_list|()
operator|.
name|setBackground
argument_list|(
name|Color
operator|.
name|green
argument_list|)
expr_stmt|;
name|getPass2Panel
argument_list|()
operator|.
name|repaint
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|vr
operator|.
name|getStatus
argument_list|()
operator|==
name|VerificationResult
operator|.
name|VERIFIED_NOTYET
condition|)
block|{
name|getPass2Panel
argument_list|()
operator|.
name|setBackground
argument_list|(
name|Color
operator|.
name|yellow
argument_list|)
expr_stmt|;
name|getPass2Panel
argument_list|()
operator|.
name|repaint
argument_list|()
expr_stmt|;
block|}
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
name|getPass2Panel
argument_list|()
operator|.
name|setBackground
argument_list|(
name|Color
operator|.
name|red
argument_list|)
expr_stmt|;
name|getPass2Panel
argument_list|()
operator|.
name|repaint
argument_list|()
expr_stmt|;
block|}
block|}
comment|/** Machine-generated. */
specifier|public
name|void
name|pass4Button_ActionPerformed
parameter_list|(
specifier|final
name|ActionEvent
name|actionEvent
parameter_list|)
block|{
name|pass2Button_ActionPerformed
argument_list|(
name|actionEvent
argument_list|)
expr_stmt|;
name|Color
name|color
init|=
name|Color
operator|.
name|green
decl_stmt|;
specifier|final
name|Verifier
name|v
init|=
name|VerifierFactory
operator|.
name|getVerifier
argument_list|(
name|className
argument_list|)
decl_stmt|;
name|VerificationResult
name|vr
init|=
name|v
operator|.
name|doPass2
argument_list|()
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
name|VERIFIED_OK
condition|)
block|{
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
name|className
argument_list|)
expr_stmt|;
specifier|final
name|int
name|nr
init|=
name|jc
operator|.
name|getMethods
argument_list|()
operator|.
name|length
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
name|nr
condition|;
name|i
operator|++
control|)
block|{
name|vr
operator|=
name|v
operator|.
name|doPass3b
argument_list|(
name|i
argument_list|)
expr_stmt|;
if|if
condition|(
name|vr
operator|.
name|getStatus
argument_list|()
operator|!=
name|VerificationResult
operator|.
name|VERIFIED_OK
condition|)
block|{
name|color
operator|=
name|Color
operator|.
name|red
expr_stmt|;
break|break;
block|}
block|}
block|}
catch|catch
parameter_list|(
specifier|final
name|ClassNotFoundException
name|ex
parameter_list|)
block|{
comment|// FIXME: report the error
name|ex
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
else|else
block|{
name|color
operator|=
name|Color
operator|.
name|yellow
expr_stmt|;
block|}
name|getPass3Panel
argument_list|()
operator|.
name|setBackground
argument_list|(
name|color
argument_list|)
expr_stmt|;
name|getPass3Panel
argument_list|()
operator|.
name|repaint
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

