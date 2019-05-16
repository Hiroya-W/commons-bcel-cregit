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
name|apache
operator|.
name|bcel
operator|.
name|Const
import|;
end_import

begin_comment
comment|/**  * Super class for the family of arithmetic instructions.  *  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|ArithmeticInstruction
extends|extends
name|Instruction
implements|implements
name|TypedInstruction
implements|,
name|StackProducer
implements|,
name|StackConsumer
block|{
comment|/**      * Empty constructor needed for Instruction.readInstruction.      * Not to be used otherwise.      */
name|ArithmeticInstruction
parameter_list|()
block|{
block|}
comment|/**      * @param opcode of instruction      */
specifier|protected
name|ArithmeticInstruction
parameter_list|(
specifier|final
name|short
name|opcode
parameter_list|)
block|{
name|super
argument_list|(
name|opcode
argument_list|,
operator|(
name|short
operator|)
literal|1
argument_list|)
expr_stmt|;
block|}
comment|/** @return type associated with the instruction      */
annotation|@
name|Override
specifier|public
name|Type
name|getType
parameter_list|(
specifier|final
name|ConstantPoolGen
name|cp
parameter_list|)
block|{
specifier|final
name|short
name|_opcode
init|=
name|super
operator|.
name|getOpcode
argument_list|()
decl_stmt|;
switch|switch
condition|(
name|_opcode
condition|)
block|{
case|case
name|Const
operator|.
name|DADD
case|:
case|case
name|Const
operator|.
name|DDIV
case|:
case|case
name|Const
operator|.
name|DMUL
case|:
case|case
name|Const
operator|.
name|DNEG
case|:
case|case
name|Const
operator|.
name|DREM
case|:
case|case
name|Const
operator|.
name|DSUB
case|:
return|return
name|Type
operator|.
name|DOUBLE
return|;
case|case
name|Const
operator|.
name|FADD
case|:
case|case
name|Const
operator|.
name|FDIV
case|:
case|case
name|Const
operator|.
name|FMUL
case|:
case|case
name|Const
operator|.
name|FNEG
case|:
case|case
name|Const
operator|.
name|FREM
case|:
case|case
name|Const
operator|.
name|FSUB
case|:
return|return
name|Type
operator|.
name|FLOAT
return|;
case|case
name|Const
operator|.
name|IADD
case|:
case|case
name|Const
operator|.
name|IAND
case|:
case|case
name|Const
operator|.
name|IDIV
case|:
case|case
name|Const
operator|.
name|IMUL
case|:
case|case
name|Const
operator|.
name|INEG
case|:
case|case
name|Const
operator|.
name|IOR
case|:
case|case
name|Const
operator|.
name|IREM
case|:
case|case
name|Const
operator|.
name|ISHL
case|:
case|case
name|Const
operator|.
name|ISHR
case|:
case|case
name|Const
operator|.
name|ISUB
case|:
case|case
name|Const
operator|.
name|IUSHR
case|:
case|case
name|Const
operator|.
name|IXOR
case|:
return|return
name|Type
operator|.
name|INT
return|;
case|case
name|Const
operator|.
name|LADD
case|:
case|case
name|Const
operator|.
name|LAND
case|:
case|case
name|Const
operator|.
name|LDIV
case|:
case|case
name|Const
operator|.
name|LMUL
case|:
case|case
name|Const
operator|.
name|LNEG
case|:
case|case
name|Const
operator|.
name|LOR
case|:
case|case
name|Const
operator|.
name|LREM
case|:
case|case
name|Const
operator|.
name|LSHL
case|:
case|case
name|Const
operator|.
name|LSHR
case|:
case|case
name|Const
operator|.
name|LSUB
case|:
case|case
name|Const
operator|.
name|LUSHR
case|:
case|case
name|Const
operator|.
name|LXOR
case|:
return|return
name|Type
operator|.
name|LONG
return|;
default|default:
comment|// Never reached
throw|throw
operator|new
name|ClassGenException
argument_list|(
literal|"Unknown type "
operator|+
name|_opcode
argument_list|)
throw|;
block|}
block|}
block|}
end_class

end_unit

