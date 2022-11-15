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
package|;
end_package

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|jupiter
operator|.
name|api
operator|.
name|Assertions
operator|.
name|assertThrows
import|;
end_import

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
name|FileInputStream
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
name|ClassFormatException
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
name|ClassParser
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|jupiter
operator|.
name|api
operator|.
name|Test
import|;
end_import

begin_class
specifier|public
class|class
name|OssFuzzTestCase
block|{
annotation|@
name|Test
specifier|public
name|void
name|testIssue51989
parameter_list|()
throws|throws
name|Exception
block|{
name|testOssFuzzReproducer
argument_list|(
literal|"51989"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testIssue52168
parameter_list|()
throws|throws
name|Exception
block|{
name|testOssFuzzReproducer
argument_list|(
literal|"52168"
argument_list|)
expr_stmt|;
block|}
specifier|private
name|void
name|testOssFuzzReproducer
parameter_list|(
name|String
name|issue
parameter_list|)
throws|throws
name|Exception
block|{
name|File
name|reproducerFile
init|=
operator|new
name|File
argument_list|(
literal|"target/test-classes/ossfuzz/issue"
operator|+
name|issue
operator|+
literal|"/Test.class"
argument_list|)
decl_stmt|;
name|FileInputStream
name|reproducerInputStream
init|=
operator|new
name|FileInputStream
argument_list|(
name|reproducerFile
argument_list|)
decl_stmt|;
name|ClassParser
name|cp
init|=
operator|new
name|ClassParser
argument_list|(
name|reproducerInputStream
argument_list|,
literal|"Test"
argument_list|)
decl_stmt|;
name|assertThrows
argument_list|(
name|ClassFormatException
operator|.
name|class
argument_list|,
parameter_list|()
lambda|->
name|cp
operator|.
name|parse
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

