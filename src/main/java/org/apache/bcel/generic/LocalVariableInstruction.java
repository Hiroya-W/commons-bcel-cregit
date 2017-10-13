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
name|java
operator|.
name|io
operator|.
name|DataOutputStream
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
name|util
operator|.
name|ByteSequence
import|;
end_import

begin_comment
comment|/**  * Abstract super class for instructions dealing with local variables.  *  * @version $Id$  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|LocalVariableInstruction
extends|extends
name|Instruction
implements|implements
name|TypedInstruction
implements|,
name|IndexedInstruction
block|{
comment|/**      * @deprecated (since 6.0) will be made private; do not access directly, use getter/setter      */
annotation|@
name|Deprecated
specifier|protected
name|int
name|n
init|=
operator|-
literal|1
decl_stmt|;
comment|// index of referenced variable
specifier|private
name|short
name|c_tag
init|=
operator|-
literal|1
decl_stmt|;
comment|// compact version, such as ILOAD_0
specifier|private
name|short
name|canon_tag
init|=
operator|-
literal|1
decl_stmt|;
comment|// canonical tag such as ILOAD
specifier|private
name|boolean
name|wide
parameter_list|()
block|{
return|return
name|n
operator|>
name|Const
operator|.
name|MAX_BYTE
return|;
block|}
comment|/**      * Empty constructor needed for Instruction.readInstruction.      * Not to be used otherwise.      * tag and length are defined in readInstruction and initFromFile, respectively.      */
name|LocalVariableInstruction
parameter_list|(
specifier|final
name|short
name|canon_tag
parameter_list|,
specifier|final
name|short
name|c_tag
parameter_list|)
block|{
name|super
argument_list|()
expr_stmt|;
name|this
operator|.
name|canon_tag
operator|=
name|canon_tag
expr_stmt|;
name|this
operator|.
name|c_tag
operator|=
name|c_tag
expr_stmt|;
block|}
comment|/**      * Empty constructor needed for Instruction.readInstruction.      * Also used by IINC()!      */
name|LocalVariableInstruction
parameter_list|()
block|{
block|}
comment|/**      * @param opcode Instruction opcode      * @param c_tag Instruction number for compact version, ALOAD_0, e.g.      * @param n local variable index (unsigned short)      */
specifier|protected
name|LocalVariableInstruction
parameter_list|(
specifier|final
name|short
name|opcode
parameter_list|,
specifier|final
name|short
name|c_tag
parameter_list|,
specifier|final
name|int
name|n
parameter_list|)
block|{
name|super
argument_list|(
name|opcode
argument_list|,
operator|(
name|short
operator|)
literal|2
argument_list|)
expr_stmt|;
name|this
operator|.
name|c_tag
operator|=
name|c_tag
expr_stmt|;
name|canon_tag
operator|=
name|opcode
expr_stmt|;
name|setIndex
argument_list|(
name|n
argument_list|)
expr_stmt|;
block|}
comment|/**      * Dump instruction as byte code to stream out.      * @param out Output stream      */
annotation|@
name|Override
specifier|public
name|void
name|dump
parameter_list|(
specifier|final
name|DataOutputStream
name|out
parameter_list|)
throws|throws
name|IOException
block|{
if|if
condition|(
name|wide
argument_list|()
condition|)
block|{
name|out
operator|.
name|writeByte
argument_list|(
name|Const
operator|.
name|WIDE
argument_list|)
expr_stmt|;
block|}
name|out
operator|.
name|writeByte
argument_list|(
name|super
operator|.
name|getOpcode
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|super
operator|.
name|getLength
argument_list|()
operator|>
literal|1
condition|)
block|{
comment|// Otherwise ILOAD_n, instruction, e.g.
if|if
condition|(
name|wide
argument_list|()
condition|)
block|{
name|out
operator|.
name|writeShort
argument_list|(
name|n
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|out
operator|.
name|writeByte
argument_list|(
name|n
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|/**      * Long output format:      *      *&lt;name of opcode&gt; "["&lt;opcode number&gt;"]"      * "("&lt;length of instruction&gt;")" "&lt;"&lt; local variable index&gt;"&gt;"      *      * @param verbose long/short format switch      * @return mnemonic for instruction      */
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|(
specifier|final
name|boolean
name|verbose
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
if|if
condition|(
operator|(
operator|(
name|_opcode
operator|>=
name|Const
operator|.
name|ILOAD_0
operator|)
operator|&&
operator|(
name|_opcode
operator|<=
name|Const
operator|.
name|ALOAD_3
operator|)
operator|)
operator|||
operator|(
operator|(
name|_opcode
operator|>=
name|Const
operator|.
name|ISTORE_0
operator|)
operator|&&
operator|(
name|_opcode
operator|<=
name|Const
operator|.
name|ASTORE_3
operator|)
operator|)
condition|)
block|{
return|return
name|super
operator|.
name|toString
argument_list|(
name|verbose
argument_list|)
return|;
block|}
return|return
name|super
operator|.
name|toString
argument_list|(
name|verbose
argument_list|)
operator|+
literal|" "
operator|+
name|n
return|;
block|}
comment|/**      * Read needed data (e.g. index) from file.      *<pre>      * (ILOAD&lt;= tag&lt;= ALOAD_3) || (ISTORE&lt;= tag&lt;= ASTORE_3)      *</pre>      */
annotation|@
name|Override
specifier|protected
name|void
name|initFromFile
parameter_list|(
specifier|final
name|ByteSequence
name|bytes
parameter_list|,
specifier|final
name|boolean
name|wide
parameter_list|)
throws|throws
name|IOException
block|{
if|if
condition|(
name|wide
condition|)
block|{
name|n
operator|=
name|bytes
operator|.
name|readUnsignedShort
argument_list|()
expr_stmt|;
name|super
operator|.
name|setLength
argument_list|(
literal|4
argument_list|)
expr_stmt|;
block|}
else|else
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
if|if
condition|(
operator|(
operator|(
name|_opcode
operator|>=
name|Const
operator|.
name|ILOAD
operator|)
operator|&&
operator|(
name|_opcode
operator|<=
name|Const
operator|.
name|ALOAD
operator|)
operator|)
operator|||
operator|(
operator|(
name|_opcode
operator|>=
name|Const
operator|.
name|ISTORE
operator|)
operator|&&
operator|(
name|_opcode
operator|<=
name|Const
operator|.
name|ASTORE
operator|)
operator|)
condition|)
block|{
name|n
operator|=
name|bytes
operator|.
name|readUnsignedByte
argument_list|()
expr_stmt|;
name|super
operator|.
name|setLength
argument_list|(
literal|2
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|_opcode
operator|<=
name|Const
operator|.
name|ALOAD_3
condition|)
block|{
comment|// compact load instruction such as ILOAD_2
name|n
operator|=
operator|(
name|_opcode
operator|-
name|Const
operator|.
name|ILOAD_0
operator|)
operator|%
literal|4
expr_stmt|;
name|super
operator|.
name|setLength
argument_list|(
literal|1
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// Assert ISTORE_0<= tag<= ASTORE_3
name|n
operator|=
operator|(
name|_opcode
operator|-
name|Const
operator|.
name|ISTORE_0
operator|)
operator|%
literal|4
expr_stmt|;
name|super
operator|.
name|setLength
argument_list|(
literal|1
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|/**      * @return local variable index (n) referred by this instruction.      */
annotation|@
name|Override
specifier|public
specifier|final
name|int
name|getIndex
parameter_list|()
block|{
return|return
name|n
return|;
block|}
comment|/**      * Set the local variable index.      * also updates opcode and length      * TODO Why?      * @see #setIndexOnly(int)      */
annotation|@
name|Override
specifier|public
name|void
name|setIndex
parameter_list|(
specifier|final
name|int
name|n
parameter_list|)
block|{
comment|// TODO could be package-protected?
if|if
condition|(
operator|(
name|n
operator|<
literal|0
operator|)
operator|||
operator|(
name|n
operator|>
name|Const
operator|.
name|MAX_SHORT
operator|)
condition|)
block|{
throw|throw
operator|new
name|ClassGenException
argument_list|(
literal|"Illegal value: "
operator|+
name|n
argument_list|)
throw|;
block|}
name|this
operator|.
name|n
operator|=
name|n
expr_stmt|;
comment|// Cannot be< 0 as this is checked above
if|if
condition|(
name|n
operator|<=
literal|3
condition|)
block|{
comment|// Use more compact instruction xLOAD_n
name|super
operator|.
name|setOpcode
argument_list|(
operator|(
name|short
operator|)
operator|(
name|c_tag
operator|+
name|n
operator|)
argument_list|)
expr_stmt|;
name|super
operator|.
name|setLength
argument_list|(
literal|1
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|super
operator|.
name|setOpcode
argument_list|(
name|canon_tag
argument_list|)
expr_stmt|;
if|if
condition|(
name|wide
argument_list|()
condition|)
block|{
name|super
operator|.
name|setLength
argument_list|(
literal|4
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|super
operator|.
name|setLength
argument_list|(
literal|2
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|/** @return canonical tag for instruction, e.g., ALOAD for ALOAD_0      */
specifier|public
name|short
name|getCanonicalTag
parameter_list|()
block|{
return|return
name|canon_tag
return|;
block|}
comment|/**      * Returns the type associated with the instruction -      * in case of ALOAD or ASTORE Type.OBJECT is returned.      * This is just a bit incorrect, because ALOAD and ASTORE      * may work on every ReferenceType (including Type.NULL) and      * ASTORE may even work on a ReturnaddressType .      * @return type associated with the instruction      */
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
switch|switch
condition|(
name|canon_tag
condition|)
block|{
case|case
name|Const
operator|.
name|ILOAD
case|:
case|case
name|Const
operator|.
name|ISTORE
case|:
return|return
name|Type
operator|.
name|INT
return|;
case|case
name|Const
operator|.
name|LLOAD
case|:
case|case
name|Const
operator|.
name|LSTORE
case|:
return|return
name|Type
operator|.
name|LONG
return|;
case|case
name|Const
operator|.
name|DLOAD
case|:
case|case
name|Const
operator|.
name|DSTORE
case|:
return|return
name|Type
operator|.
name|DOUBLE
return|;
case|case
name|Const
operator|.
name|FLOAD
case|:
case|case
name|Const
operator|.
name|FSTORE
case|:
return|return
name|Type
operator|.
name|FLOAT
return|;
case|case
name|Const
operator|.
name|ALOAD
case|:
case|case
name|Const
operator|.
name|ASTORE
case|:
return|return
name|Type
operator|.
name|OBJECT
return|;
default|default:
throw|throw
operator|new
name|ClassGenException
argument_list|(
literal|"Oops: unknown case in switch"
operator|+
name|canon_tag
argument_list|)
throw|;
block|}
block|}
comment|/**      * Sets the index of the referenced variable (n) only      * @since 6.0      * @see #setIndex(int)      */
specifier|final
name|void
name|setIndexOnly
parameter_list|(
specifier|final
name|int
name|n
parameter_list|)
block|{
name|this
operator|.
name|n
operator|=
name|n
expr_stmt|;
block|}
block|}
end_class

end_unit

