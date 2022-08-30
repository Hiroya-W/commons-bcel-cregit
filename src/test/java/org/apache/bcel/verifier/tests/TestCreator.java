begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreements.  See the NOTICE file distributed with  * this work for additional information regarding copyright ownership.  * The ASF licenses this file to You under the Apache License, Version 2.0  * (the "License"); you may not use this file except in compliance with  * the License.  You may obtain a copy of the License at  *  *   http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS,  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  * See the License for the specific language governing permissions and  * limitations under the License.  *  */
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
operator|.
name|tests
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|File
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|FileOutputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|OutputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URISyntaxException
import|;
end_import

begin_class
specifier|public
specifier|abstract
class|class
name|TestCreator
block|{
comment|// Common package base name for generated test classes
specifier|protected
specifier|static
specifier|final
name|String
name|TEST_PACKAGE
init|=
name|TestCreator
operator|.
name|class
operator|.
name|getPackage
argument_list|()
operator|.
name|getName
argument_list|()
decl_stmt|;
specifier|public
name|void
name|create
parameter_list|()
throws|throws
name|IOException
block|{
specifier|final
name|File
name|classFile
init|=
operator|new
name|File
argument_list|(
name|getPackageFolder
argument_list|()
argument_list|,
name|getClassName
argument_list|()
argument_list|)
decl_stmt|;
try|try
init|(
name|FileOutputStream
name|out
init|=
operator|new
name|FileOutputStream
argument_list|(
name|classFile
argument_list|)
init|)
block|{
name|create
argument_list|(
name|out
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
specifier|abstract
name|void
name|create
parameter_list|(
name|OutputStream
name|out
parameter_list|)
throws|throws
name|IOException
function_decl|;
specifier|private
name|File
name|getClassesFolder
parameter_list|()
throws|throws
name|IOException
block|{
try|try
block|{
return|return
operator|new
name|File
argument_list|(
name|getClass
argument_list|()
operator|.
name|getProtectionDomain
argument_list|()
operator|.
name|getCodeSource
argument_list|()
operator|.
name|getLocation
argument_list|()
operator|.
name|toURI
argument_list|()
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
specifier|final
name|URISyntaxException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IOException
argument_list|(
name|e
argument_list|)
throw|;
block|}
block|}
specifier|private
name|String
name|getClassName
parameter_list|()
block|{
specifier|final
name|String
name|name
init|=
name|getClass
argument_list|()
operator|.
name|getName
argument_list|()
decl_stmt|;
return|return
name|name
operator|.
name|substring
argument_list|(
name|name
operator|.
name|lastIndexOf
argument_list|(
literal|'.'
argument_list|)
operator|+
literal|1
argument_list|)
operator|.
name|replace
argument_list|(
literal|"Creator"
argument_list|,
literal|".class"
argument_list|)
return|;
block|}
specifier|private
name|File
name|getPackageFolder
parameter_list|()
throws|throws
name|IOException
block|{
return|return
operator|new
name|File
argument_list|(
name|getClassesFolder
argument_list|()
argument_list|,
name|getPackageName
argument_list|()
argument_list|)
return|;
block|}
specifier|protected
name|String
name|getPackageName
parameter_list|()
block|{
return|return
name|getClass
argument_list|()
operator|.
name|getPackage
argument_list|()
operator|.
name|getName
argument_list|()
operator|.
name|replace
argument_list|(
literal|'.'
argument_list|,
literal|'/'
argument_list|)
return|;
block|}
block|}
end_class

end_unit

