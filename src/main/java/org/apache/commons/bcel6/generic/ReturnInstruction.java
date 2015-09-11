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
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|bcel6
operator|.
name|Constants
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
name|ExceptionConst
import|;
end_import

begin_comment
comment|/**  * Super class for the xRETURN family of instructions.  *  * @version $Id$  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|ReturnInstruction
extends|extends
name|Instruction
implements|implements
name|ExceptionThrower
implements|,
name|TypedInstruction
implements|,
name|StackConsumer
block|{
comment|/**      * Empty constructor needed for the Class.newInstance() statement in      * Instruction.readInstruction(). Not to be used otherwise.      */
name|ReturnInstruction
parameter_list|()
block|{
block|}
comment|/**      * @param opcode of instruction      */
specifier|protected
name|ReturnInstruction
parameter_list|(
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
specifier|public
name|Type
name|getType
parameter_list|()
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
name|Constants
operator|.
name|IRETURN
case|:
return|return
name|Type
operator|.
name|INT
return|;
case|case
name|Constants
operator|.
name|LRETURN
case|:
return|return
name|Type
operator|.
name|LONG
return|;
case|case
name|Constants
operator|.
name|FRETURN
case|:
return|return
name|Type
operator|.
name|FLOAT
return|;
case|case
name|Constants
operator|.
name|DRETURN
case|:
return|return
name|Type
operator|.
name|DOUBLE
return|;
case|case
name|Constants
operator|.
name|ARETURN
case|:
return|return
name|Type
operator|.
name|OBJECT
return|;
case|case
name|Constants
operator|.
name|RETURN
case|:
return|return
name|Type
operator|.
name|VOID
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
annotation|@
name|Override
specifier|public
name|Class
argument_list|<
name|?
argument_list|>
index|[]
name|getExceptions
parameter_list|()
block|{
return|return
operator|new
name|Class
index|[]
block|{
name|ExceptionConst
operator|.
name|ILLEGAL_MONITOR_STATE
block|}
return|;
block|}
comment|/** @return type associated with the instruction      */
annotation|@
name|Override
specifier|public
name|Type
name|getType
parameter_list|(
name|ConstantPoolGen
name|cp
parameter_list|)
block|{
return|return
name|getType
argument_list|()
return|;
block|}
block|}
end_class

end_unit

