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
operator|.
name|util
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|PrintWriter
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Locale
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
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
name|classfile
operator|.
name|Utility
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
name|AllocationInstruction
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
name|ArrayInstruction
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
name|ArrayType
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
name|BranchHandle
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
name|BranchInstruction
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
name|CHECKCAST
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
name|CPInstruction
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
name|CodeExceptionGen
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
name|ConstantPushInstruction
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
name|EmptyVisitor
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
name|FieldInstruction
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
name|IINC
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
name|INSTANCEOF
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
name|Instruction
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
name|InvokeInstruction
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
name|LDC
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
name|LDC2_W
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
name|LocalVariableInstruction
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
name|MULTIANEWARRAY
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
name|NEWARRAY
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
name|RET
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
name|ReturnInstruction
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
name|Select
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

begin_comment
comment|/**  * Factory creates il.append() statements, and sets instruction targets. A helper class for BCELifier.  *  * @see BCELifier  */
end_comment

begin_class
class|class
name|BCELFactory
extends|extends
name|EmptyVisitor
block|{
specifier|private
specifier|static
specifier|final
name|String
name|CONSTANT_PREFIX
init|=
name|Const
operator|.
name|class
operator|.
name|getSimpleName
argument_list|()
operator|+
literal|"."
decl_stmt|;
specifier|private
specifier|final
name|MethodGen
name|methodGen
decl_stmt|;
specifier|private
specifier|final
name|PrintWriter
name|printWriter
decl_stmt|;
specifier|private
specifier|final
name|ConstantPoolGen
name|constantPoolGen
decl_stmt|;
specifier|private
specifier|final
name|Map
argument_list|<
name|Instruction
argument_list|,
name|InstructionHandle
argument_list|>
name|branchMap
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
comment|// Memorize BranchInstructions that need an update
specifier|private
specifier|final
name|List
argument_list|<
name|BranchInstruction
argument_list|>
name|branches
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|BCELFactory
parameter_list|(
specifier|final
name|MethodGen
name|mg
parameter_list|,
specifier|final
name|PrintWriter
name|out
parameter_list|)
block|{
name|methodGen
operator|=
name|mg
expr_stmt|;
name|constantPoolGen
operator|=
name|mg
operator|.
name|getConstantPool
argument_list|()
expr_stmt|;
name|printWriter
operator|=
name|out
expr_stmt|;
block|}
specifier|private
name|void
name|createConstant
parameter_list|(
specifier|final
name|Object
name|value
parameter_list|)
block|{
name|String
name|embed
init|=
name|value
operator|.
name|toString
argument_list|()
decl_stmt|;
if|if
condition|(
name|value
operator|instanceof
name|String
condition|)
block|{
name|embed
operator|=
literal|'"'
operator|+
name|Utility
operator|.
name|convertString
argument_list|(
name|embed
argument_list|)
operator|+
literal|'"'
expr_stmt|;
block|}
if|else if
condition|(
name|value
operator|instanceof
name|Character
condition|)
block|{
name|embed
operator|=
literal|"(char)0x"
operator|+
name|Integer
operator|.
name|toHexString
argument_list|(
operator|(
operator|(
name|Character
operator|)
name|value
operator|)
operator|.
name|charValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|value
operator|instanceof
name|Float
condition|)
block|{
specifier|final
name|Float
name|f
init|=
operator|(
name|Float
operator|)
name|value
decl_stmt|;
if|if
condition|(
name|Float
operator|.
name|isNaN
argument_list|(
name|f
argument_list|)
condition|)
block|{
name|embed
operator|=
literal|"Float.NaN"
expr_stmt|;
block|}
if|else if
condition|(
name|f
operator|==
name|Float
operator|.
name|POSITIVE_INFINITY
condition|)
block|{
name|embed
operator|=
literal|"Float.POSITIVE_INFINITY"
expr_stmt|;
block|}
if|else if
condition|(
name|f
operator|==
name|Float
operator|.
name|NEGATIVE_INFINITY
condition|)
block|{
name|embed
operator|=
literal|"Float.NEGATIVE_INFINITY"
expr_stmt|;
block|}
else|else
block|{
name|embed
operator|+=
literal|"f"
expr_stmt|;
block|}
block|}
if|else if
condition|(
name|value
operator|instanceof
name|Double
condition|)
block|{
specifier|final
name|Double
name|d
init|=
operator|(
name|Double
operator|)
name|value
decl_stmt|;
if|if
condition|(
name|Double
operator|.
name|isNaN
argument_list|(
name|d
argument_list|)
condition|)
block|{
name|embed
operator|=
literal|"Double.NaN"
expr_stmt|;
block|}
if|else if
condition|(
name|d
operator|==
name|Double
operator|.
name|POSITIVE_INFINITY
condition|)
block|{
name|embed
operator|=
literal|"Double.POSITIVE_INFINITY"
expr_stmt|;
block|}
if|else if
condition|(
name|d
operator|==
name|Double
operator|.
name|NEGATIVE_INFINITY
condition|)
block|{
name|embed
operator|=
literal|"Double.NEGATIVE_INFINITY"
expr_stmt|;
block|}
else|else
block|{
name|embed
operator|+=
literal|"d"
expr_stmt|;
block|}
block|}
if|else if
condition|(
name|value
operator|instanceof
name|Long
condition|)
block|{
name|embed
operator|+=
literal|"L"
expr_stmt|;
block|}
if|else if
condition|(
name|value
operator|instanceof
name|ObjectType
condition|)
block|{
specifier|final
name|ObjectType
name|ot
init|=
operator|(
name|ObjectType
operator|)
name|value
decl_stmt|;
name|embed
operator|=
literal|"new ObjectType(\""
operator|+
name|ot
operator|.
name|getClassName
argument_list|()
operator|+
literal|"\")"
expr_stmt|;
block|}
if|else if
condition|(
name|value
operator|instanceof
name|ArrayType
condition|)
block|{
specifier|final
name|ArrayType
name|at
init|=
operator|(
name|ArrayType
operator|)
name|value
decl_stmt|;
name|embed
operator|=
literal|"new ArrayType("
operator|+
name|BCELifier
operator|.
name|printType
argument_list|(
name|at
operator|.
name|getBasicType
argument_list|()
argument_list|)
operator|+
literal|", "
operator|+
name|at
operator|.
name|getDimensions
argument_list|()
operator|+
literal|")"
expr_stmt|;
block|}
name|printWriter
operator|.
name|println
argument_list|(
literal|"il.append(new PUSH(_cp, "
operator|+
name|embed
operator|+
literal|"));"
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|start
parameter_list|()
block|{
if|if
condition|(
operator|!
name|methodGen
operator|.
name|isAbstract
argument_list|()
operator|&&
operator|!
name|methodGen
operator|.
name|isNative
argument_list|()
condition|)
block|{
for|for
control|(
name|InstructionHandle
name|ih
init|=
name|methodGen
operator|.
name|getInstructionList
argument_list|()
operator|.
name|getStart
argument_list|()
init|;
name|ih
operator|!=
literal|null
condition|;
name|ih
operator|=
name|ih
operator|.
name|getNext
argument_list|()
control|)
block|{
specifier|final
name|Instruction
name|i
init|=
name|ih
operator|.
name|getInstruction
argument_list|()
decl_stmt|;
if|if
condition|(
name|i
operator|instanceof
name|BranchInstruction
condition|)
block|{
name|branchMap
operator|.
name|put
argument_list|(
name|i
argument_list|,
name|ih
argument_list|)
expr_stmt|;
comment|// memorize container
block|}
if|if
condition|(
name|ih
operator|.
name|hasTargeters
argument_list|()
condition|)
block|{
if|if
condition|(
name|i
operator|instanceof
name|BranchInstruction
condition|)
block|{
name|printWriter
operator|.
name|println
argument_list|(
literal|"    InstructionHandle ih_"
operator|+
name|ih
operator|.
name|getPosition
argument_list|()
operator|+
literal|";"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|printWriter
operator|.
name|print
argument_list|(
literal|"    InstructionHandle ih_"
operator|+
name|ih
operator|.
name|getPosition
argument_list|()
operator|+
literal|" = "
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|printWriter
operator|.
name|print
argument_list|(
literal|"    "
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|visitInstruction
argument_list|(
name|i
argument_list|)
condition|)
block|{
name|i
operator|.
name|accept
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
block|}
name|updateBranchTargets
argument_list|()
expr_stmt|;
name|updateExceptionHandlers
argument_list|()
expr_stmt|;
block|}
block|}
specifier|private
name|void
name|updateBranchTargets
parameter_list|()
block|{
name|branches
operator|.
name|forEach
argument_list|(
name|bi
lambda|->
block|{
specifier|final
name|BranchHandle
name|bh
init|=
operator|(
name|BranchHandle
operator|)
name|branchMap
operator|.
name|get
argument_list|(
name|bi
argument_list|)
decl_stmt|;
specifier|final
name|int
name|pos
init|=
name|bh
operator|.
name|getPosition
argument_list|()
decl_stmt|;
specifier|final
name|String
name|name
init|=
name|bi
operator|.
name|getName
argument_list|()
operator|+
literal|"_"
operator|+
name|pos
decl_stmt|;
name|int
name|targetPos
init|=
name|bh
operator|.
name|getTarget
argument_list|()
operator|.
name|getPosition
argument_list|()
decl_stmt|;
name|printWriter
operator|.
name|println
argument_list|(
literal|"    "
operator|+
name|name
operator|+
literal|".setTarget(ih_"
operator|+
name|targetPos
operator|+
literal|");"
argument_list|)
expr_stmt|;
if|if
condition|(
name|bi
operator|instanceof
name|Select
condition|)
block|{
specifier|final
name|InstructionHandle
index|[]
name|ihs
init|=
operator|(
operator|(
name|Select
operator|)
name|bi
operator|)
operator|.
name|getTargets
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|j
init|=
literal|0
init|;
name|j
operator|<
name|ihs
operator|.
name|length
condition|;
name|j
operator|++
control|)
block|{
name|targetPos
operator|=
name|ihs
index|[
name|j
index|]
operator|.
name|getPosition
argument_list|()
expr_stmt|;
name|printWriter
operator|.
name|println
argument_list|(
literal|"    "
operator|+
name|name
operator|+
literal|".setTarget("
operator|+
name|j
operator|+
literal|", ih_"
operator|+
name|targetPos
operator|+
literal|");"
argument_list|)
expr_stmt|;
block|}
block|}
block|}
argument_list|)
expr_stmt|;
block|}
specifier|private
name|void
name|updateExceptionHandlers
parameter_list|()
block|{
specifier|final
name|CodeExceptionGen
index|[]
name|handlers
init|=
name|methodGen
operator|.
name|getExceptionHandlers
argument_list|()
decl_stmt|;
for|for
control|(
specifier|final
name|CodeExceptionGen
name|h
range|:
name|handlers
control|)
block|{
specifier|final
name|String
name|type
init|=
name|h
operator|.
name|getCatchType
argument_list|()
operator|==
literal|null
condition|?
literal|"null"
else|:
name|BCELifier
operator|.
name|printType
argument_list|(
name|h
operator|.
name|getCatchType
argument_list|()
argument_list|)
decl_stmt|;
name|printWriter
operator|.
name|println
argument_list|(
literal|"    method.addExceptionHandler("
operator|+
literal|"ih_"
operator|+
name|h
operator|.
name|getStartPC
argument_list|()
operator|.
name|getPosition
argument_list|()
operator|+
literal|", "
operator|+
literal|"ih_"
operator|+
name|h
operator|.
name|getEndPC
argument_list|()
operator|.
name|getPosition
argument_list|()
operator|+
literal|", "
operator|+
literal|"ih_"
operator|+
name|h
operator|.
name|getHandlerPC
argument_list|()
operator|.
name|getPosition
argument_list|()
operator|+
literal|", "
operator|+
name|type
operator|+
literal|");"
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|visitAllocationInstruction
parameter_list|(
specifier|final
name|AllocationInstruction
name|i
parameter_list|)
block|{
name|Type
name|type
decl_stmt|;
if|if
condition|(
name|i
operator|instanceof
name|CPInstruction
condition|)
block|{
name|type
operator|=
operator|(
operator|(
name|CPInstruction
operator|)
name|i
operator|)
operator|.
name|getType
argument_list|(
name|constantPoolGen
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|type
operator|=
operator|(
operator|(
name|NEWARRAY
operator|)
name|i
operator|)
operator|.
name|getType
argument_list|()
expr_stmt|;
block|}
specifier|final
name|short
name|opcode
init|=
operator|(
operator|(
name|Instruction
operator|)
name|i
operator|)
operator|.
name|getOpcode
argument_list|()
decl_stmt|;
name|int
name|dim
init|=
literal|1
decl_stmt|;
switch|switch
condition|(
name|opcode
condition|)
block|{
case|case
name|Const
operator|.
name|NEW
case|:
name|printWriter
operator|.
name|println
argument_list|(
literal|"il.append(_factory.createNew(\""
operator|+
operator|(
operator|(
name|ObjectType
operator|)
name|type
operator|)
operator|.
name|getClassName
argument_list|()
operator|+
literal|"\"));"
argument_list|)
expr_stmt|;
break|break;
case|case
name|Const
operator|.
name|MULTIANEWARRAY
case|:
name|dim
operator|=
operator|(
operator|(
name|MULTIANEWARRAY
operator|)
name|i
operator|)
operator|.
name|getDimensions
argument_list|()
expr_stmt|;
comment|//$FALL-THROUGH$
case|case
name|Const
operator|.
name|NEWARRAY
case|:
if|if
condition|(
name|type
operator|instanceof
name|ArrayType
condition|)
block|{
name|type
operator|=
operator|(
operator|(
name|ArrayType
operator|)
name|type
operator|)
operator|.
name|getBasicType
argument_list|()
expr_stmt|;
block|}
comment|//$FALL-THROUGH$
case|case
name|Const
operator|.
name|ANEWARRAY
case|:
name|printWriter
operator|.
name|println
argument_list|(
literal|"il.append(_factory.createNewArray("
operator|+
name|BCELifier
operator|.
name|printType
argument_list|(
name|type
argument_list|)
operator|+
literal|", (short) "
operator|+
name|dim
operator|+
literal|"));"
argument_list|)
expr_stmt|;
break|break;
default|default:
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Unhandled opcode: "
operator|+
name|opcode
argument_list|)
throw|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|visitArrayInstruction
parameter_list|(
specifier|final
name|ArrayInstruction
name|i
parameter_list|)
block|{
specifier|final
name|short
name|opcode
init|=
name|i
operator|.
name|getOpcode
argument_list|()
decl_stmt|;
specifier|final
name|Type
name|type
init|=
name|i
operator|.
name|getType
argument_list|(
name|constantPoolGen
argument_list|)
decl_stmt|;
specifier|final
name|String
name|kind
init|=
name|opcode
operator|<
name|Const
operator|.
name|IASTORE
condition|?
literal|"Load"
else|:
literal|"Store"
decl_stmt|;
name|printWriter
operator|.
name|println
argument_list|(
literal|"il.append(_factory.createArray"
operator|+
name|kind
operator|+
literal|"("
operator|+
name|BCELifier
operator|.
name|printType
argument_list|(
name|type
argument_list|)
operator|+
literal|"));"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|visitBranchInstruction
parameter_list|(
specifier|final
name|BranchInstruction
name|bi
parameter_list|)
block|{
specifier|final
name|BranchHandle
name|bh
init|=
operator|(
name|BranchHandle
operator|)
name|branchMap
operator|.
name|get
argument_list|(
name|bi
argument_list|)
decl_stmt|;
specifier|final
name|int
name|pos
init|=
name|bh
operator|.
name|getPosition
argument_list|()
decl_stmt|;
specifier|final
name|String
name|name
init|=
name|bi
operator|.
name|getName
argument_list|()
operator|+
literal|"_"
operator|+
name|pos
decl_stmt|;
if|if
condition|(
name|bi
operator|instanceof
name|Select
condition|)
block|{
specifier|final
name|Select
name|s
init|=
operator|(
name|Select
operator|)
name|bi
decl_stmt|;
name|branches
operator|.
name|add
argument_list|(
name|bi
argument_list|)
expr_stmt|;
specifier|final
name|StringBuilder
name|args
init|=
operator|new
name|StringBuilder
argument_list|(
literal|"new int[] { "
argument_list|)
decl_stmt|;
specifier|final
name|int
index|[]
name|matchs
init|=
name|s
operator|.
name|getMatchs
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|matchs
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|args
operator|.
name|append
argument_list|(
name|matchs
index|[
name|i
index|]
argument_list|)
expr_stmt|;
if|if
condition|(
name|i
operator|<
name|matchs
operator|.
name|length
operator|-
literal|1
condition|)
block|{
name|args
operator|.
name|append
argument_list|(
literal|", "
argument_list|)
expr_stmt|;
block|}
block|}
name|args
operator|.
name|append
argument_list|(
literal|" }"
argument_list|)
expr_stmt|;
name|printWriter
operator|.
name|print
argument_list|(
literal|"Select "
operator|+
name|name
operator|+
literal|" = new "
operator|+
name|bi
operator|.
name|getName
argument_list|()
operator|.
name|toUpperCase
argument_list|(
name|Locale
operator|.
name|ENGLISH
argument_list|)
operator|+
literal|"("
operator|+
name|args
operator|+
literal|", new InstructionHandle[] { "
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|matchs
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|printWriter
operator|.
name|print
argument_list|(
literal|"null"
argument_list|)
expr_stmt|;
if|if
condition|(
name|i
operator|<
name|matchs
operator|.
name|length
operator|-
literal|1
condition|)
block|{
name|printWriter
operator|.
name|print
argument_list|(
literal|", "
argument_list|)
expr_stmt|;
block|}
block|}
name|printWriter
operator|.
name|println
argument_list|(
literal|" }, null);"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
specifier|final
name|int
name|tPos
init|=
name|bh
operator|.
name|getTarget
argument_list|()
operator|.
name|getPosition
argument_list|()
decl_stmt|;
name|String
name|target
decl_stmt|;
if|if
condition|(
name|pos
operator|>
name|tPos
condition|)
block|{
name|target
operator|=
literal|"ih_"
operator|+
name|tPos
expr_stmt|;
block|}
else|else
block|{
name|branches
operator|.
name|add
argument_list|(
name|bi
argument_list|)
expr_stmt|;
name|target
operator|=
literal|"null"
expr_stmt|;
block|}
name|printWriter
operator|.
name|println
argument_list|(
literal|"    BranchInstruction "
operator|+
name|name
operator|+
literal|" = _factory.createBranchInstruction("
operator|+
name|CONSTANT_PREFIX
operator|+
name|bi
operator|.
name|getName
argument_list|()
operator|.
name|toUpperCase
argument_list|(
name|Locale
operator|.
name|ENGLISH
argument_list|)
operator|+
literal|", "
operator|+
name|target
operator|+
literal|");"
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|bh
operator|.
name|hasTargeters
argument_list|()
condition|)
block|{
name|printWriter
operator|.
name|println
argument_list|(
literal|"    ih_"
operator|+
name|pos
operator|+
literal|" = il.append("
operator|+
name|name
operator|+
literal|");"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|printWriter
operator|.
name|println
argument_list|(
literal|"    il.append("
operator|+
name|name
operator|+
literal|");"
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|visitCHECKCAST
parameter_list|(
specifier|final
name|CHECKCAST
name|i
parameter_list|)
block|{
specifier|final
name|Type
name|type
init|=
name|i
operator|.
name|getType
argument_list|(
name|constantPoolGen
argument_list|)
decl_stmt|;
name|printWriter
operator|.
name|println
argument_list|(
literal|"il.append(_factory.createCheckCast("
operator|+
name|BCELifier
operator|.
name|printType
argument_list|(
name|type
argument_list|)
operator|+
literal|"));"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|visitConstantPushInstruction
parameter_list|(
specifier|final
name|ConstantPushInstruction
name|i
parameter_list|)
block|{
name|createConstant
argument_list|(
name|i
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|visitFieldInstruction
parameter_list|(
specifier|final
name|FieldInstruction
name|i
parameter_list|)
block|{
specifier|final
name|short
name|opcode
init|=
name|i
operator|.
name|getOpcode
argument_list|()
decl_stmt|;
specifier|final
name|String
name|className
init|=
name|i
operator|.
name|getClassName
argument_list|(
name|constantPoolGen
argument_list|)
decl_stmt|;
specifier|final
name|String
name|fieldName
init|=
name|i
operator|.
name|getFieldName
argument_list|(
name|constantPoolGen
argument_list|)
decl_stmt|;
specifier|final
name|Type
name|type
init|=
name|i
operator|.
name|getFieldType
argument_list|(
name|constantPoolGen
argument_list|)
decl_stmt|;
name|printWriter
operator|.
name|println
argument_list|(
literal|"il.append(_factory.createFieldAccess(\""
operator|+
name|className
operator|+
literal|"\", \""
operator|+
name|fieldName
operator|+
literal|"\", "
operator|+
name|BCELifier
operator|.
name|printType
argument_list|(
name|type
argument_list|)
operator|+
literal|", "
operator|+
name|CONSTANT_PREFIX
operator|+
name|Const
operator|.
name|getOpcodeName
argument_list|(
name|opcode
argument_list|)
operator|.
name|toUpperCase
argument_list|(
name|Locale
operator|.
name|ENGLISH
argument_list|)
operator|+
literal|"));"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|visitINSTANCEOF
parameter_list|(
specifier|final
name|INSTANCEOF
name|i
parameter_list|)
block|{
specifier|final
name|Type
name|type
init|=
name|i
operator|.
name|getType
argument_list|(
name|constantPoolGen
argument_list|)
decl_stmt|;
name|printWriter
operator|.
name|println
argument_list|(
literal|"il.append(_factory.createInstanceOf("
operator|+
name|BCELifier
operator|.
name|printType
argument_list|(
name|type
argument_list|)
operator|+
literal|"));"
argument_list|)
expr_stmt|;
block|}
specifier|private
name|boolean
name|visitInstruction
parameter_list|(
specifier|final
name|Instruction
name|i
parameter_list|)
block|{
specifier|final
name|short
name|opcode
init|=
name|i
operator|.
name|getOpcode
argument_list|()
decl_stmt|;
if|if
condition|(
name|InstructionConst
operator|.
name|getInstruction
argument_list|(
name|opcode
argument_list|)
operator|!=
literal|null
operator|&&
operator|!
operator|(
name|i
operator|instanceof
name|ConstantPushInstruction
operator|)
operator|&&
operator|!
operator|(
name|i
operator|instanceof
name|ReturnInstruction
operator|)
condition|)
block|{
comment|// Handled below
name|printWriter
operator|.
name|println
argument_list|(
literal|"il.append(InstructionConst."
operator|+
name|i
operator|.
name|getName
argument_list|()
operator|.
name|toUpperCase
argument_list|(
name|Locale
operator|.
name|ENGLISH
argument_list|)
operator|+
literal|");"
argument_list|)
expr_stmt|;
return|return
literal|true
return|;
block|}
return|return
literal|false
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|visitInvokeInstruction
parameter_list|(
specifier|final
name|InvokeInstruction
name|i
parameter_list|)
block|{
specifier|final
name|short
name|opcode
init|=
name|i
operator|.
name|getOpcode
argument_list|()
decl_stmt|;
specifier|final
name|String
name|className
init|=
name|i
operator|.
name|getClassName
argument_list|(
name|constantPoolGen
argument_list|)
decl_stmt|;
specifier|final
name|String
name|methodName
init|=
name|i
operator|.
name|getMethodName
argument_list|(
name|constantPoolGen
argument_list|)
decl_stmt|;
specifier|final
name|Type
name|type
init|=
name|i
operator|.
name|getReturnType
argument_list|(
name|constantPoolGen
argument_list|)
decl_stmt|;
specifier|final
name|Type
index|[]
name|argTypes
init|=
name|i
operator|.
name|getArgumentTypes
argument_list|(
name|constantPoolGen
argument_list|)
decl_stmt|;
name|printWriter
operator|.
name|println
argument_list|(
literal|"il.append(_factory.createInvoke(\""
operator|+
name|className
operator|+
literal|"\", \""
operator|+
name|methodName
operator|+
literal|"\", "
operator|+
name|BCELifier
operator|.
name|printType
argument_list|(
name|type
argument_list|)
operator|+
literal|", "
operator|+
name|BCELifier
operator|.
name|printArgumentTypes
argument_list|(
name|argTypes
argument_list|)
operator|+
literal|", "
operator|+
name|CONSTANT_PREFIX
operator|+
name|Const
operator|.
name|getOpcodeName
argument_list|(
name|opcode
argument_list|)
operator|.
name|toUpperCase
argument_list|(
name|Locale
operator|.
name|ENGLISH
argument_list|)
operator|+
literal|"));"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|visitLDC
parameter_list|(
specifier|final
name|LDC
name|i
parameter_list|)
block|{
name|createConstant
argument_list|(
name|i
operator|.
name|getValue
argument_list|(
name|constantPoolGen
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|visitLDC2_W
parameter_list|(
specifier|final
name|LDC2_W
name|i
parameter_list|)
block|{
name|createConstant
argument_list|(
name|i
operator|.
name|getValue
argument_list|(
name|constantPoolGen
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|visitLocalVariableInstruction
parameter_list|(
specifier|final
name|LocalVariableInstruction
name|i
parameter_list|)
block|{
specifier|final
name|short
name|opcode
init|=
name|i
operator|.
name|getOpcode
argument_list|()
decl_stmt|;
specifier|final
name|Type
name|type
init|=
name|i
operator|.
name|getType
argument_list|(
name|constantPoolGen
argument_list|)
decl_stmt|;
if|if
condition|(
name|opcode
operator|==
name|Const
operator|.
name|IINC
condition|)
block|{
name|printWriter
operator|.
name|println
argument_list|(
literal|"il.append(new IINC("
operator|+
name|i
operator|.
name|getIndex
argument_list|()
operator|+
literal|", "
operator|+
operator|(
operator|(
name|IINC
operator|)
name|i
operator|)
operator|.
name|getIncrement
argument_list|()
operator|+
literal|"));"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
specifier|final
name|String
name|kind
init|=
name|opcode
operator|<
name|Const
operator|.
name|ISTORE
condition|?
literal|"Load"
else|:
literal|"Store"
decl_stmt|;
name|printWriter
operator|.
name|println
argument_list|(
literal|"il.append(_factory.create"
operator|+
name|kind
operator|+
literal|"("
operator|+
name|BCELifier
operator|.
name|printType
argument_list|(
name|type
argument_list|)
operator|+
literal|", "
operator|+
name|i
operator|.
name|getIndex
argument_list|()
operator|+
literal|"));"
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|visitRET
parameter_list|(
specifier|final
name|RET
name|i
parameter_list|)
block|{
name|printWriter
operator|.
name|println
argument_list|(
literal|"il.append(new RET("
operator|+
name|i
operator|.
name|getIndex
argument_list|()
operator|+
literal|"));"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|visitReturnInstruction
parameter_list|(
specifier|final
name|ReturnInstruction
name|i
parameter_list|)
block|{
specifier|final
name|Type
name|type
init|=
name|i
operator|.
name|getType
argument_list|(
name|constantPoolGen
argument_list|)
decl_stmt|;
name|printWriter
operator|.
name|println
argument_list|(
literal|"il.append(_factory.createReturn("
operator|+
name|BCELifier
operator|.
name|printType
argument_list|(
name|type
argument_list|)
operator|+
literal|"));"
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

