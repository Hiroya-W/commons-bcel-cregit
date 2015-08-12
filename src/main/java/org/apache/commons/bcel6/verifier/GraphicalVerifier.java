begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreements.  See the NOTICE file distributed with  * this work for additional information regarding copyright ownership.  * The ASF licenses this file to You under the Apache License, Version 2.0  * (the "License"); you may not use this file except in compliance with  * the License.  You may obtain a copy of the License at  *  *      http://www.apache.org/licenses/LICENSE-2.0  *  *  Unless required by applicable law or agreed to in writing, software  *  distributed under the License is distributed on an "AS IS" BASIS,  *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *  See the License for the specific language governing permissions and  *  limitations under the License.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|bcel6
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
name|Dimension
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Toolkit
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|UIManager
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|bcel6
operator|.
name|generic
operator|.
name|Type
import|;
end_import

begin_comment
comment|/**  * A graphical user interface application demonstrating JustIce.  *  * @version $Id$  */
end_comment

begin_class
specifier|public
class|class
name|GraphicalVerifier
block|{
name|boolean
name|packFrame
init|=
literal|false
decl_stmt|;
comment|/** Constructor. */
specifier|public
name|GraphicalVerifier
parameter_list|()
block|{
name|VerifierAppFrame
name|frame
init|=
operator|new
name|VerifierAppFrame
argument_list|()
decl_stmt|;
comment|//Frames ï¿½berprï¿½fen, die voreingestellte Grï¿½ï¿½e haben
comment|//Frames packen, die nutzbare bevorzugte Grï¿½ï¿½eninformationen enthalten, z.B. aus ihrem Layout
if|if
condition|(
name|packFrame
condition|)
block|{
name|frame
operator|.
name|pack
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|frame
operator|.
name|validate
argument_list|()
expr_stmt|;
block|}
comment|//Das Fenster zentrieren
name|Dimension
name|screenSize
init|=
name|Toolkit
operator|.
name|getDefaultToolkit
argument_list|()
operator|.
name|getScreenSize
argument_list|()
decl_stmt|;
name|Dimension
name|frameSize
init|=
name|frame
operator|.
name|getSize
argument_list|()
decl_stmt|;
if|if
condition|(
name|frameSize
operator|.
name|height
operator|>
name|screenSize
operator|.
name|height
condition|)
block|{
name|frameSize
operator|.
name|height
operator|=
name|screenSize
operator|.
name|height
expr_stmt|;
block|}
if|if
condition|(
name|frameSize
operator|.
name|width
operator|>
name|screenSize
operator|.
name|width
condition|)
block|{
name|frameSize
operator|.
name|width
operator|=
name|screenSize
operator|.
name|width
expr_stmt|;
block|}
name|frame
operator|.
name|setLocation
argument_list|(
operator|(
name|screenSize
operator|.
name|width
operator|-
name|frameSize
operator|.
name|width
operator|)
operator|/
literal|2
argument_list|,
operator|(
name|screenSize
operator|.
name|height
operator|-
name|frameSize
operator|.
name|height
operator|)
operator|/
literal|2
argument_list|)
expr_stmt|;
name|frame
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|frame
operator|.
name|classNamesJList
operator|.
name|setModel
argument_list|(
operator|new
name|VerifierFactoryListModel
argument_list|()
argument_list|)
expr_stmt|;
name|VerifierFactory
operator|.
name|getVerifier
argument_list|(
name|Type
operator|.
name|OBJECT
operator|.
name|getClassName
argument_list|()
argument_list|)
expr_stmt|;
comment|// Fill list with java.lang.Object
name|frame
operator|.
name|classNamesJList
operator|.
name|setSelectedIndex
argument_list|(
literal|0
argument_list|)
expr_stmt|;
comment|// default, will verify java.lang.Object
block|}
comment|/** Main method. */
specifier|public
specifier|static
name|void
name|main
parameter_list|(
name|String
index|[]
name|args
parameter_list|)
block|{
try|try
block|{
name|UIManager
operator|.
name|setLookAndFeel
argument_list|(
name|UIManager
operator|.
name|getSystemLookAndFeelClassName
argument_list|()
argument_list|)
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
operator|new
name|GraphicalVerifier
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

