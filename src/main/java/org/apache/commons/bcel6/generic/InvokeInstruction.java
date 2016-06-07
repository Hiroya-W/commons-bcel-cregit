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
name|generic
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|StringTokenizer
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
name|Const
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
name|classfile
operator|.
name|Constant
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
name|classfile
operator|.
name|ConstantCP
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
name|classfile
operator|.
name|ConstantPool
import|;
end_import

begin_comment
comment|/**  * Super class for the INVOKExxx family of instructions.  *  * @version $Id$  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|InvokeInstruction
extends|extends
name|FieldOrMethod
implements|implements
name|ExceptionThrower
implements|,
name|StackConsumer
implements|,
name|StackProducer
block|{
comment|/**      * Empty constructor needed for the Class.newInstance() statement in      * Instruction.readInstruction(). Not to be used otherwise.      */
name|InvokeInstruction
parameter_list|()
block|{
block|}
comment|/**      * @param index to constant pool      */
specifier|protected
name|InvokeInstruction
parameter_list|(
specifier|final
name|short
name|opcode
parameter_list|,
specifier|final
name|int
name|index
parameter_list|)
block|{
name|super
argument_list|(
name|opcode
argument_list|,
name|index
argument_list|)
expr_stmt|;
block|}
comment|/**      * @return mnemonic for instruction with symbolic references resolved      */
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|(
specifier|final
name|ConstantPool
name|cp
parameter_list|)
block|{
name|Constant
name|c
init|=
name|cp
operator|.
name|getConstant
argument_list|(
name|super
operator|.
name|getIndex
argument_list|()
argument_list|)
decl_stmt|;
name|StringTokenizer
name|tok
init|=
operator|new
name|StringTokenizer
argument_list|(
name|cp
operator|.
name|constantToString
argument_list|(
name|c
argument_list|)
argument_list|)
decl_stmt|;
return|return
name|Const
operator|.
name|getOpcodeName
argument_list|(
name|super
operator|.
name|getOpcode
argument_list|()
argument_list|)
operator|+
literal|" "
operator|+
name|tok
operator|.
name|nextToken
argument_list|()
operator|.
name|replace
argument_list|(
literal|'.'
argument_list|,
literal|'/'
argument_list|)
operator|+
name|tok
operator|.
name|nextToken
argument_list|()
return|;
block|}
comment|/**      * Also works for instructions whose stack effect depends on the      * constant pool entry they reference.      * @return Number of words consumed from stack by this instruction      */
annotation|@
name|Override
specifier|public
name|int
name|consumeStack
parameter_list|(
specifier|final
name|ConstantPoolGen
name|cpg
parameter_list|)
block|{
name|int
name|sum
decl_stmt|;
if|if
condition|(
operator|(
name|super
operator|.
name|getOpcode
argument_list|()
operator|==
name|Const
operator|.
name|INVOKESTATIC
operator|)
operator|||
operator|(
name|super
operator|.
name|getOpcode
argument_list|()
operator|==
name|Const
operator|.
name|INVOKEDYNAMIC
operator|)
condition|)
block|{
name|sum
operator|=
literal|0
expr_stmt|;
block|}
else|else
block|{
name|sum
operator|=
literal|1
expr_stmt|;
comment|// this reference
block|}
name|String
name|signature
init|=
name|getSignature
argument_list|(
name|cpg
argument_list|)
decl_stmt|;
name|sum
operator|+=
name|Type
operator|.
name|getArgumentTypesSize
argument_list|(
name|signature
argument_list|)
expr_stmt|;
return|return
name|sum
return|;
block|}
comment|/**      * Also works for instructions whose stack effect depends on the      * constant pool entry they reference.      * @return Number of words produced onto stack by this instruction      */
annotation|@
name|Override
specifier|public
name|int
name|produceStack
parameter_list|(
specifier|final
name|ConstantPoolGen
name|cpg
parameter_list|)
block|{
name|String
name|signature
init|=
name|getSignature
argument_list|(
name|cpg
argument_list|)
decl_stmt|;
return|return
name|Type
operator|.
name|getReturnTypeSize
argument_list|(
name|signature
argument_list|)
return|;
block|}
comment|/** @return return type of referenced method.      */
annotation|@
name|Override
specifier|public
name|Type
name|getType
parameter_list|(
specifier|final
name|ConstantPoolGen
name|cpg
parameter_list|)
block|{
return|return
name|getReturnType
argument_list|(
name|cpg
argument_list|)
return|;
block|}
comment|/** @return name of referenced method.      */
specifier|public
name|String
name|getMethodName
parameter_list|(
specifier|final
name|ConstantPoolGen
name|cpg
parameter_list|)
block|{
return|return
name|getName
argument_list|(
name|cpg
argument_list|)
return|;
block|}
comment|/** @return return type of referenced method.      */
specifier|public
name|Type
name|getReturnType
parameter_list|(
specifier|final
name|ConstantPoolGen
name|cpg
parameter_list|)
block|{
return|return
name|Type
operator|.
name|getReturnType
argument_list|(
name|getSignature
argument_list|(
name|cpg
argument_list|)
argument_list|)
return|;
block|}
comment|/** @return argument types of referenced method.      */
specifier|public
name|Type
index|[]
name|getArgumentTypes
parameter_list|(
specifier|final
name|ConstantPoolGen
name|cpg
parameter_list|)
block|{
return|return
name|Type
operator|.
name|getArgumentTypes
argument_list|(
name|getSignature
argument_list|(
name|cpg
argument_list|)
argument_list|)
return|;
block|}
block|}
end_class

end_unit

