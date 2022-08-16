begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreements.  See the NOTICE file distributed with  * this work for additional information regarding copyright ownership.  * The ASF licenses this file to You under the Apache License, Version 2.0  * (the "License"); you may not use this file except in compliance with  * the License.  You may obtain a copy of the License at  *  *   http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS,  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  * See the License for the specific language governing permissions and  * limitations under the License.  */
end_comment

begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|generic
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
name|assertDoesNotThrow
import|;
end_import

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
name|assertTrue
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
name|AbstractTestCase
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
name|junit
operator|.
name|jupiter
operator|.
name|api
operator|.
name|Test
import|;
end_import

begin_comment
comment|/**  * Verify that a constant pool containing dynamic constants (condy) - in this case created by JaCoCo - can be read and  * the corresponding constant is found after parsing.  */
end_comment

begin_class
specifier|public
class|class
name|JiraBcel362TestCase
extends|extends
name|AbstractTestCase
block|{
annotation|@
name|Test
specifier|public
name|void
name|testProcessConstantPoolWithCondyEntry
parameter_list|()
throws|throws
name|ClassNotFoundException
block|{
specifier|final
name|JavaClass
name|clazz
init|=
name|getTestClass
argument_list|(
literal|"issue362.Bcel362"
argument_list|)
decl_stmt|;
specifier|final
name|ConstantPoolGen
name|constantPoolGen
init|=
name|assertDoesNotThrow
argument_list|(
parameter_list|()
lambda|->
operator|new
name|ConstantPoolGen
argument_list|(
name|clazz
operator|.
name|getConstantPool
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|constantPoolGen
operator|.
name|lookupUtf8
argument_list|(
literal|"$jacocoData"
argument_list|)
operator|!=
operator|-
literal|1
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

