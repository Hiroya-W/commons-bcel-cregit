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
name|assertEquals
import|;
end_import

begin_class
specifier|public
class|class
name|I2LTestCase
block|{
annotation|@
name|Test
specifier|public
name|void
name|accept
parameter_list|()
block|{
specifier|final
name|CountVisitor
name|countVisitor
init|=
operator|new
name|CountVisitor
argument_list|()
decl_stmt|;
specifier|final
name|I2L
name|i2l
init|=
operator|new
name|I2L
argument_list|()
decl_stmt|;
name|i2l
operator|.
name|accept
argument_list|(
name|countVisitor
argument_list|)
expr_stmt|;
specifier|final
name|CountVisitor
name|expected
init|=
operator|new
name|CountVisitor
argument_list|()
decl_stmt|;
name|expected
operator|.
name|setTypedInstruction
argument_list|(
literal|1
argument_list|)
expr_stmt|;
name|expected
operator|.
name|setStackProducer
argument_list|(
literal|1
argument_list|)
expr_stmt|;
name|expected
operator|.
name|setStackConsumer
argument_list|(
literal|1
argument_list|)
expr_stmt|;
name|expected
operator|.
name|setConversionInstruction
argument_list|(
literal|1
argument_list|)
expr_stmt|;
name|expected
operator|.
name|setI2L
argument_list|(
literal|1
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|expected
argument_list|,
name|countVisitor
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

