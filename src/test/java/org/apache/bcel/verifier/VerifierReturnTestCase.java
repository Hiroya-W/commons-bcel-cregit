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
name|verifier
package|;
end_package

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
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|verifier
operator|.
name|tests
operator|.
name|TestReturn01Creator
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
name|verifier
operator|.
name|tests
operator|.
name|TestReturn03BooleanCreator
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
name|verifier
operator|.
name|tests
operator|.
name|TestReturn03ByteCreator
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
name|verifier
operator|.
name|tests
operator|.
name|TestReturn03DoubleCreator
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
name|verifier
operator|.
name|tests
operator|.
name|TestReturn03FloatCreator
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
name|verifier
operator|.
name|tests
operator|.
name|TestReturn03IntCreator
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
name|verifier
operator|.
name|tests
operator|.
name|TestReturn03LongCreator
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
name|verifier
operator|.
name|tests
operator|.
name|TestReturn03ObjectCreator
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
name|verifier
operator|.
name|tests
operator|.
name|TestReturn03UnknownCreator
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
name|Assertions
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
class|class
name|VerifierReturnTestCase
extends|extends
name|AbstractVerifierTestCase
block|{
annotation|@
name|Test
specifier|public
name|void
name|testInvalidReturn
parameter_list|()
throws|throws
name|IOException
throws|,
name|ClassNotFoundException
block|{
operator|new
name|TestReturn01Creator
argument_list|()
operator|.
name|create
argument_list|()
expr_stmt|;
name|assertVerifyRejected
argument_list|(
literal|"TestReturn01"
argument_list|,
literal|"Verification of a void method that returns an object must fail."
argument_list|)
expr_stmt|;
operator|new
name|TestReturn03IntCreator
argument_list|()
operator|.
name|create
argument_list|()
expr_stmt|;
name|assertVerifyRejected
argument_list|(
literal|"TestReturn03Int"
argument_list|,
literal|"Verification of an int method that returns null int must fail."
argument_list|)
expr_stmt|;
operator|new
name|TestReturn03FloatCreator
argument_list|()
operator|.
name|create
argument_list|()
expr_stmt|;
name|assertVerifyRejected
argument_list|(
literal|"TestReturn03Float"
argument_list|,
literal|"Verification of a int method that returns null float must fail."
argument_list|)
expr_stmt|;
operator|new
name|TestReturn03DoubleCreator
argument_list|()
operator|.
name|create
argument_list|()
expr_stmt|;
name|assertVerifyRejected
argument_list|(
literal|"TestReturn03Double"
argument_list|,
literal|"Verification of a int method that returns null double must fail."
argument_list|)
expr_stmt|;
operator|new
name|TestReturn03LongCreator
argument_list|()
operator|.
name|create
argument_list|()
expr_stmt|;
name|assertVerifyRejected
argument_list|(
literal|"TestReturn03Long"
argument_list|,
literal|"Verification of a int method that returns null long must fail."
argument_list|)
expr_stmt|;
operator|new
name|TestReturn03ByteCreator
argument_list|()
operator|.
name|create
argument_list|()
expr_stmt|;
name|assertVerifyRejected
argument_list|(
literal|"TestReturn03Byte"
argument_list|,
literal|"Verification of a int method that returns null byte must fail."
argument_list|)
expr_stmt|;
operator|new
name|TestReturn03BooleanCreator
argument_list|()
operator|.
name|create
argument_list|()
expr_stmt|;
name|assertVerifyRejected
argument_list|(
literal|"TestReturn03Boolean"
argument_list|,
literal|"Verification of a int method that returns null boolean must fail."
argument_list|)
expr_stmt|;
operator|new
name|TestReturn03ObjectCreator
argument_list|()
operator|.
name|create
argument_list|()
expr_stmt|;
name|assertVerifyRejected
argument_list|(
literal|"TestReturn03Object"
argument_list|,
literal|"Verification of a int method that returns null Object must fail."
argument_list|)
expr_stmt|;
specifier|final
name|TestReturn03UnknownCreator
name|testReturn03UnknownCreator
init|=
operator|new
name|TestReturn03UnknownCreator
argument_list|()
decl_stmt|;
name|Assertions
operator|.
name|assertThrowsExactly
argument_list|(
name|IllegalArgumentException
operator|.
name|class
argument_list|,
name|testReturn03UnknownCreator
operator|::
name|create
argument_list|,
literal|"Invalid type<unknown object>"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testValidReturn
parameter_list|()
throws|throws
name|ClassNotFoundException
block|{
name|assertVerifyOK
argument_list|(
literal|"TestReturn02"
argument_list|,
literal|"Verification of a method that returns a newly created object must pass."
argument_list|)
expr_stmt|;
name|assertVerifyOK
argument_list|(
literal|"TestArray01"
argument_list|,
literal|"Verification of a method that returns an array must pass."
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

