begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreements.  See the NOTICE file distributed with  * this work for additional information regarding copyright ownership.  * The ASF licenses this file to You under the Apache License, Version 2.0  * (the "License"); you may not use this file except in compliance with  * the License.  You may obtain a copy of the License at  *  *      http://www.apache.org/licenses/LICENSE-2.0  *  *  Unless required by applicable law or agreed to in writing, software  *  distributed under the License is distributed on an "AS IS" BASIS,  *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *  See the License for the specific language governing permissions and  *  limitations under the License.   *  */
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
name|Constants
import|;
end_import

begin_comment
comment|/**  * Super class for the family of arithmetic instructions.  *  * @version $Id$  * @author<A HREF="mailto:m.dahm@gmx.de">M. Dahm</A>  */
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
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|5027221136148765144L
decl_stmt|;
comment|/**      * Empty constructor needed for the Class.newInstance() statement in      * Instruction.readInstruction(). Not to be used otherwise.      */
name|ArithmeticInstruction
parameter_list|()
block|{
block|}
comment|/**      * @param opcode of instruction      */
specifier|protected
name|ArithmeticInstruction
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
comment|/** @return type associated with the instruction      */
specifier|public
name|Type
name|getType
parameter_list|(
name|ConstantPoolGen
name|cp
parameter_list|)
block|{
switch|switch
condition|(
name|opcode
condition|)
block|{
case|case
name|Constants
operator|.
name|DADD
case|:
case|case
name|Constants
operator|.
name|DDIV
case|:
case|case
name|Constants
operator|.
name|DMUL
case|:
case|case
name|Constants
operator|.
name|DNEG
case|:
case|case
name|Constants
operator|.
name|DREM
case|:
case|case
name|Constants
operator|.
name|DSUB
case|:
return|return
name|Type
operator|.
name|DOUBLE
return|;
case|case
name|Constants
operator|.
name|FADD
case|:
case|case
name|Constants
operator|.
name|FDIV
case|:
case|case
name|Constants
operator|.
name|FMUL
case|:
case|case
name|Constants
operator|.
name|FNEG
case|:
case|case
name|Constants
operator|.
name|FREM
case|:
case|case
name|Constants
operator|.
name|FSUB
case|:
return|return
name|Type
operator|.
name|FLOAT
return|;
case|case
name|Constants
operator|.
name|IADD
case|:
case|case
name|Constants
operator|.
name|IAND
case|:
case|case
name|Constants
operator|.
name|IDIV
case|:
case|case
name|Constants
operator|.
name|IMUL
case|:
case|case
name|Constants
operator|.
name|INEG
case|:
case|case
name|Constants
operator|.
name|IOR
case|:
case|case
name|Constants
operator|.
name|IREM
case|:
case|case
name|Constants
operator|.
name|ISHL
case|:
case|case
name|Constants
operator|.
name|ISHR
case|:
case|case
name|Constants
operator|.
name|ISUB
case|:
case|case
name|Constants
operator|.
name|IUSHR
case|:
case|case
name|Constants
operator|.
name|IXOR
case|:
return|return
name|Type
operator|.
name|INT
return|;
case|case
name|Constants
operator|.
name|LADD
case|:
case|case
name|Constants
operator|.
name|LAND
case|:
case|case
name|Constants
operator|.
name|LDIV
case|:
case|case
name|Constants
operator|.
name|LMUL
case|:
case|case
name|Constants
operator|.
name|LNEG
case|:
case|case
name|Constants
operator|.
name|LOR
case|:
case|case
name|Constants
operator|.
name|LREM
case|:
case|case
name|Constants
operator|.
name|LSHL
case|:
case|case
name|Constants
operator|.
name|LSHR
case|:
case|case
name|Constants
operator|.
name|LSUB
case|:
case|case
name|Constants
operator|.
name|LUSHR
case|:
case|case
name|Constants
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
name|opcode
argument_list|)
throw|;
block|}
block|}
block|}
end_class

end_unit

