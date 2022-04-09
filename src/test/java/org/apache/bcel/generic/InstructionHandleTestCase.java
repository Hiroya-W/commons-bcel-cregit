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
name|assertNotNull
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
name|assertThrows
import|;
end_import

begin_class
specifier|public
class|class
name|InstructionHandleTestCase
block|{
comment|// Test that setInstruction only allows Instructions that are not BranchInstructions
annotation|@
name|Test
specifier|public
name|void
name|testsetInstructionNull
parameter_list|()
block|{
specifier|final
name|InstructionHandle
name|ih
init|=
name|InstructionHandle
operator|.
name|getInstructionHandle
argument_list|(
operator|new
name|NOP
argument_list|()
argument_list|)
decl_stmt|;
comment|// have to start with a valid non BI
name|assertNotNull
argument_list|(
name|ih
argument_list|)
expr_stmt|;
name|assertThrows
argument_list|(
name|ClassGenException
operator|.
name|class
argument_list|,
parameter_list|()
lambda|->
name|ih
operator|.
name|setInstruction
argument_list|(
literal|null
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testsetInstructionI
parameter_list|()
block|{
specifier|final
name|InstructionHandle
name|ih
init|=
name|InstructionHandle
operator|.
name|getInstructionHandle
argument_list|(
operator|new
name|NOP
argument_list|()
argument_list|)
decl_stmt|;
comment|// have to start with a valid non BI
name|assertNotNull
argument_list|(
name|ih
argument_list|)
expr_stmt|;
name|ih
operator|.
name|setInstruction
argument_list|(
operator|new
name|NOP
argument_list|()
argument_list|)
expr_stmt|;
name|assertNotNull
argument_list|(
name|ih
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testsetInstructionnotI
parameter_list|()
block|{
specifier|final
name|InstructionHandle
name|ih
init|=
name|InstructionHandle
operator|.
name|getInstructionHandle
argument_list|(
operator|new
name|NOP
argument_list|()
argument_list|)
decl_stmt|;
comment|// have to start with a valid non BI
name|assertNotNull
argument_list|(
name|ih
argument_list|)
expr_stmt|;
name|assertThrows
argument_list|(
name|ClassGenException
operator|.
name|class
argument_list|,
parameter_list|()
lambda|->
name|ih
operator|.
name|setInstruction
argument_list|(
operator|new
name|GOTO
argument_list|(
literal|null
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testGetIHnull
parameter_list|()
block|{
name|assertThrows
argument_list|(
name|ClassGenException
operator|.
name|class
argument_list|,
parameter_list|()
lambda|->
name|InstructionHandle
operator|.
name|getInstructionHandle
argument_list|(
literal|null
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testBCEL195
parameter_list|()
block|{
specifier|final
name|InstructionList
name|il
init|=
operator|new
name|InstructionList
argument_list|()
decl_stmt|;
specifier|final
name|InstructionHandle
name|ih
init|=
name|il
operator|.
name|append
argument_list|(
name|InstructionConst
operator|.
name|NOP
argument_list|)
decl_stmt|;
operator|new
name|TABLESWITCH
argument_list|(
operator|new
name|int
index|[
literal|0
index|]
argument_list|,
name|InstructionHandle
operator|.
name|EMPTY_ARRAY
argument_list|,
name|ih
argument_list|)
expr_stmt|;
operator|new
name|TABLESWITCH
argument_list|(
operator|new
name|int
index|[
literal|0
index|]
argument_list|,
name|InstructionHandle
operator|.
name|EMPTY_ARRAY
argument_list|,
name|ih
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

