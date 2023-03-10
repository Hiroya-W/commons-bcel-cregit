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
operator|.
name|tests
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
name|assertNotNull
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
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|Const
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
name|generic
operator|.
name|ClassGen
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
name|generic
operator|.
name|ConstantPoolGen
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
name|generic
operator|.
name|InstructionConst
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
name|generic
operator|.
name|InstructionFactory
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
name|generic
operator|.
name|InstructionHandle
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
name|generic
operator|.
name|InstructionList
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
name|generic
operator|.
name|MethodGen
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
name|generic
operator|.
name|ObjectType
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
name|generic
operator|.
name|PUSH
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
name|generic
operator|.
name|Type
import|;
end_import

begin_class
specifier|public
class|class
name|TestArrayAccess02Creator
extends|extends
name|TestCreator
block|{
specifier|private
specifier|final
name|InstructionFactory
name|instructionFactory
decl_stmt|;
specifier|private
specifier|final
name|ConstantPoolGen
name|constantPoolGen
decl_stmt|;
specifier|private
specifier|final
name|ClassGen
name|classGen
decl_stmt|;
specifier|public
name|TestArrayAccess02Creator
parameter_list|()
block|{
name|classGen
operator|=
operator|new
name|ClassGen
argument_list|(
name|TEST_PACKAGE
operator|+
literal|".TestArrayAccess02"
argument_list|,
literal|"java.lang.Object"
argument_list|,
literal|"TestArrayAccess02.java"
argument_list|,
name|Const
operator|.
name|ACC_PUBLIC
operator||
name|Const
operator|.
name|ACC_SUPER
argument_list|,
operator|new
name|String
index|[]
block|{}
argument_list|)
expr_stmt|;
name|constantPoolGen
operator|=
name|classGen
operator|.
name|getConstantPool
argument_list|()
expr_stmt|;
name|instructionFactory
operator|=
operator|new
name|InstructionFactory
argument_list|(
name|classGen
argument_list|,
name|constantPoolGen
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|create
parameter_list|(
specifier|final
name|OutputStream
name|out
parameter_list|)
throws|throws
name|IOException
block|{
name|createMethod_0
argument_list|()
expr_stmt|;
name|createMethod_1
argument_list|()
expr_stmt|;
name|classGen
operator|.
name|getJavaClass
argument_list|()
operator|.
name|dump
argument_list|(
name|out
argument_list|)
expr_stmt|;
block|}
specifier|private
name|void
name|createMethod_0
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
name|MethodGen
name|method
init|=
operator|new
name|MethodGen
argument_list|(
name|Const
operator|.
name|ACC_PUBLIC
argument_list|,
name|Type
operator|.
name|VOID
argument_list|,
name|Type
operator|.
name|NO_ARGS
argument_list|,
operator|new
name|String
index|[]
block|{}
argument_list|,
literal|"<init>"
argument_list|,
name|TEST_PACKAGE
operator|+
literal|".TestArrayAccess02"
argument_list|,
name|il
argument_list|,
name|constantPoolGen
argument_list|)
decl_stmt|;
specifier|final
name|InstructionHandle
name|ih_0
init|=
name|il
operator|.
name|append
argument_list|(
name|InstructionFactory
operator|.
name|createLoad
argument_list|(
name|Type
operator|.
name|OBJECT
argument_list|,
literal|0
argument_list|)
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
name|ih_0
argument_list|)
expr_stmt|;
comment|// TODO why is this not used
name|il
operator|.
name|append
argument_list|(
name|instructionFactory
operator|.
name|createInvoke
argument_list|(
literal|"java.lang.Object"
argument_list|,
literal|"<init>"
argument_list|,
name|Type
operator|.
name|VOID
argument_list|,
name|Type
operator|.
name|NO_ARGS
argument_list|,
name|Const
operator|.
name|INVOKESPECIAL
argument_list|)
argument_list|)
expr_stmt|;
specifier|final
name|InstructionHandle
name|ih_4
init|=
name|il
operator|.
name|append
argument_list|(
name|InstructionFactory
operator|.
name|createReturn
argument_list|(
name|Type
operator|.
name|VOID
argument_list|)
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
name|ih_4
argument_list|)
expr_stmt|;
comment|// TODO why is this not used
name|method
operator|.
name|setMaxStack
argument_list|()
expr_stmt|;
name|method
operator|.
name|setMaxLocals
argument_list|()
expr_stmt|;
name|classGen
operator|.
name|addMethod
argument_list|(
name|method
operator|.
name|getMethod
argument_list|()
argument_list|)
expr_stmt|;
name|il
operator|.
name|dispose
argument_list|()
expr_stmt|;
block|}
specifier|private
name|void
name|createMethod_1
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
name|MethodGen
name|method
init|=
operator|new
name|MethodGen
argument_list|(
name|Const
operator|.
name|ACC_PUBLIC
operator||
name|Const
operator|.
name|ACC_STATIC
argument_list|,
name|Type
operator|.
name|VOID
argument_list|,
name|Type
operator|.
name|NO_ARGS
argument_list|,
operator|new
name|String
index|[]
block|{}
argument_list|,
literal|"test"
argument_list|,
name|TEST_PACKAGE
operator|+
literal|".TestArrayAccess02"
argument_list|,
name|il
argument_list|,
name|constantPoolGen
argument_list|)
decl_stmt|;
specifier|final
name|InstructionHandle
name|ih_0
init|=
name|il
operator|.
name|append
argument_list|(
operator|new
name|PUSH
argument_list|(
name|constantPoolGen
argument_list|,
literal|1
argument_list|)
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
name|ih_0
argument_list|)
expr_stmt|;
comment|// TODO why is this not used
name|il
operator|.
name|append
argument_list|(
name|instructionFactory
operator|.
name|createNewArray
argument_list|(
operator|new
name|ObjectType
argument_list|(
name|TEST_PACKAGE
operator|+
literal|".TestArrayAccess02"
argument_list|)
argument_list|,
operator|(
name|short
operator|)
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|il
operator|.
name|append
argument_list|(
name|InstructionFactory
operator|.
name|createStore
argument_list|(
name|Type
operator|.
name|OBJECT
argument_list|,
literal|0
argument_list|)
argument_list|)
expr_stmt|;
specifier|final
name|InstructionHandle
name|ih_5
init|=
name|il
operator|.
name|append
argument_list|(
operator|new
name|PUSH
argument_list|(
name|constantPoolGen
argument_list|,
literal|1
argument_list|)
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
name|ih_5
argument_list|)
expr_stmt|;
comment|// TODO why is this not used
name|il
operator|.
name|append
argument_list|(
name|instructionFactory
operator|.
name|createNewArray
argument_list|(
name|Type
operator|.
name|STRING
argument_list|,
operator|(
name|short
operator|)
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|il
operator|.
name|append
argument_list|(
name|InstructionFactory
operator|.
name|createStore
argument_list|(
name|Type
operator|.
name|OBJECT
argument_list|,
literal|1
argument_list|)
argument_list|)
expr_stmt|;
specifier|final
name|InstructionHandle
name|ih_10
init|=
name|il
operator|.
name|append
argument_list|(
name|InstructionFactory
operator|.
name|createLoad
argument_list|(
name|Type
operator|.
name|OBJECT
argument_list|,
literal|1
argument_list|)
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
name|ih_10
argument_list|)
expr_stmt|;
comment|// TODO why is this not used
name|il
operator|.
name|append
argument_list|(
operator|new
name|PUSH
argument_list|(
name|constantPoolGen
argument_list|,
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|il
operator|.
name|append
argument_list|(
name|instructionFactory
operator|.
name|createNew
argument_list|(
name|TEST_PACKAGE
operator|+
literal|".TestArrayAccess02"
argument_list|)
argument_list|)
expr_stmt|;
name|il
operator|.
name|append
argument_list|(
name|InstructionConst
operator|.
name|DUP
argument_list|)
expr_stmt|;
name|il
operator|.
name|append
argument_list|(
name|instructionFactory
operator|.
name|createInvoke
argument_list|(
name|TEST_PACKAGE
operator|+
literal|".TestArrayAccess02"
argument_list|,
literal|"<init>"
argument_list|,
name|Type
operator|.
name|VOID
argument_list|,
name|Type
operator|.
name|NO_ARGS
argument_list|,
name|Const
operator|.
name|INVOKESPECIAL
argument_list|)
argument_list|)
expr_stmt|;
name|il
operator|.
name|append
argument_list|(
name|InstructionConst
operator|.
name|AASTORE
argument_list|)
expr_stmt|;
specifier|final
name|InstructionHandle
name|ih_20
init|=
name|il
operator|.
name|append
argument_list|(
name|InstructionFactory
operator|.
name|createReturn
argument_list|(
name|Type
operator|.
name|VOID
argument_list|)
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
name|ih_20
argument_list|)
expr_stmt|;
comment|// TODO why is this not used
name|method
operator|.
name|setMaxStack
argument_list|()
expr_stmt|;
name|method
operator|.
name|setMaxLocals
argument_list|()
expr_stmt|;
name|classGen
operator|.
name|addMethod
argument_list|(
name|method
operator|.
name|getMethod
argument_list|()
argument_list|)
expr_stmt|;
name|il
operator|.
name|dispose
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

