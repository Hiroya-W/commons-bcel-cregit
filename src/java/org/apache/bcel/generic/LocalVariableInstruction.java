begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
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

begin_comment
comment|/* ====================================================================  * The Apache Software License, Version 1.1  *  * Copyright (c) 2001 The Apache Software Foundation.  All rights  * reserved.  *  * Redistribution and use in source and binary forms, with or without  * modification, are permitted provided that the following conditions  * are met:  *  * 1. Redistributions of source code must retain the above copyright  *    notice, this list of conditions and the following disclaimer.  *  * 2. Redistributions in binary form must reproduce the above copyright  *    notice, this list of conditions and the following disclaimer in  *    the documentation and/or other materials provided with the  *    distribution.  *  * 3. The end-user documentation included with the redistribution,  *    if any, must include the following acknowledgment:  *       "This product includes software developed by the  *        Apache Software Foundation (http://www.apache.org/)."  *    Alternately, this acknowledgment may appear in the software itself,  *    if and wherever such third-party acknowledgments normally appear.  *  * 4. The names "Apache" and "Apache Software Foundation" and  *    "Apache BCEL" must not be used to endorse or promote products  *    derived from this software without prior written permission. For  *    written permission, please contact apache@apache.org.  *  * 5. Products derived from this software may not be called "Apache",  *    "Apache BCEL", nor may "Apache" appear in their name, without  *    prior written permission of the Apache Software Foundation.  *  * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED  * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES  * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE  * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR  * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,  * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT  * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF  * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND  * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,  * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT  * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF  * SUCH DAMAGE.  * ====================================================================  *  * This software consists of voluntary contributions made by many  * individuals on behalf of the Apache Software Foundation.  For more  * information on the Apache Software Foundation, please see  *<http://www.apache.org/>.  */
end_comment

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|*
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
name|Constants
import|;
end_import

begin_comment
comment|/**  * Abstract super class for instructions dealing with local variables.  *  * @version $Id$  * @author<A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>  */
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
specifier|final
name|boolean
name|wide
parameter_list|()
block|{
return|return
name|n
operator|>
name|Constants
operator|.
name|MAX_BYTE
return|;
block|}
comment|/**    * Empty constructor needed for the Class.newInstance() statement in    * Instruction.readInstruction(). Not to be used otherwise.    * tag and length are defined in readInstruction and initFromFile, respectively.    */
name|LocalVariableInstruction
parameter_list|(
name|short
name|canon_tag
parameter_list|,
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
comment|/**    * Empty constructor needed for the Class.newInstance() statement in    * Instruction.readInstruction(). Also used by IINC()!    */
name|LocalVariableInstruction
parameter_list|()
block|{
block|}
comment|/**    * @param opcode Instruction opcode    * @param c_tag Instruction number for compact version, ALOAD_0, e.g.    * @param n local variable index (unsigned short)    */
specifier|protected
name|LocalVariableInstruction
parameter_list|(
name|short
name|opcode
parameter_list|,
name|short
name|c_tag
parameter_list|,
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
comment|/**    * Dump instruction as byte code to stream out.    * @param out Output stream    */
specifier|public
name|void
name|dump
parameter_list|(
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
comment|// Need WIDE prefix ?
name|out
operator|.
name|writeByte
argument_list|(
name|Constants
operator|.
name|WIDE
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeByte
argument_list|(
name|opcode
argument_list|)
expr_stmt|;
if|if
condition|(
name|length
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
name|out
operator|.
name|writeShort
argument_list|(
name|n
argument_list|)
expr_stmt|;
else|else
name|out
operator|.
name|writeByte
argument_list|(
name|n
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**    * Long output format:    *    *&lt;name of opcode&gt; "["&lt;opcode number&gt;"]"     * "("&lt;length of instruction&gt;")" "&lt;"&lt; local variable index&gt;"&gt;"    *    * @param verbose long/short format switch    * @return mnemonic for instruction    */
specifier|public
name|String
name|toString
parameter_list|(
name|boolean
name|verbose
parameter_list|)
block|{
if|if
condition|(
operator|(
operator|(
name|opcode
operator|>=
name|Constants
operator|.
name|ILOAD_0
operator|)
operator|&&
operator|(
name|opcode
operator|<=
name|Constants
operator|.
name|ALOAD_3
operator|)
operator|)
operator|||
operator|(
operator|(
name|opcode
operator|>=
name|Constants
operator|.
name|ISTORE_0
operator|)
operator|&&
operator|(
name|opcode
operator|<=
name|Constants
operator|.
name|ASTORE_3
operator|)
operator|)
condition|)
return|return
name|super
operator|.
name|toString
argument_list|(
name|verbose
argument_list|)
return|;
else|else
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
comment|/**    * Read needed data (e.g. index) from file.    * PRE: (ILOAD<= tag<= ALOAD_3) || (ISTORE<= tag<= ASTORE_3)    */
specifier|protected
name|void
name|initFromFile
parameter_list|(
name|ByteSequence
name|bytes
parameter_list|,
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
name|length
operator|=
literal|4
expr_stmt|;
block|}
if|else if
condition|(
operator|(
operator|(
name|opcode
operator|>=
name|Constants
operator|.
name|ILOAD
operator|)
operator|&&
operator|(
name|opcode
operator|<=
name|Constants
operator|.
name|ALOAD
operator|)
operator|)
operator|||
operator|(
operator|(
name|opcode
operator|>=
name|Constants
operator|.
name|ISTORE
operator|)
operator|&&
operator|(
name|opcode
operator|<=
name|Constants
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
name|length
operator|=
literal|2
expr_stmt|;
block|}
if|else if
condition|(
name|opcode
operator|<=
name|Constants
operator|.
name|ALOAD_3
condition|)
block|{
comment|// compact load instruction such as ILOAD_2
name|n
operator|=
operator|(
name|opcode
operator|-
name|Constants
operator|.
name|ILOAD_0
operator|)
operator|%
literal|4
expr_stmt|;
name|length
operator|=
literal|1
expr_stmt|;
block|}
else|else
block|{
comment|// Assert ISTORE_0<= tag<= ASTORE_3
name|n
operator|=
operator|(
name|opcode
operator|-
name|Constants
operator|.
name|ISTORE_0
operator|)
operator|%
literal|4
expr_stmt|;
name|length
operator|=
literal|1
expr_stmt|;
block|}
block|}
comment|/**    * @return local variable index  referred by this instruction.    */
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
comment|/**    * Set the local variable index    */
specifier|public
name|void
name|setIndex
parameter_list|(
name|int
name|n
parameter_list|)
block|{
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
name|Constants
operator|.
name|MAX_SHORT
operator|)
condition|)
throw|throw
operator|new
name|ClassGenException
argument_list|(
literal|"Illegal value: "
operator|+
name|n
argument_list|)
throw|;
name|this
operator|.
name|n
operator|=
name|n
expr_stmt|;
if|if
condition|(
name|n
operator|>=
literal|0
operator|&&
name|n
operator|<=
literal|3
condition|)
block|{
comment|// Use more compact instruction xLOAD_n
name|opcode
operator|=
operator|(
name|short
operator|)
operator|(
name|c_tag
operator|+
name|n
operator|)
expr_stmt|;
name|length
operator|=
literal|1
expr_stmt|;
block|}
else|else
block|{
name|opcode
operator|=
name|canon_tag
expr_stmt|;
if|if
condition|(
name|wide
argument_list|()
condition|)
comment|// Need WIDE prefix ?
name|length
operator|=
literal|4
expr_stmt|;
else|else
name|length
operator|=
literal|2
expr_stmt|;
block|}
block|}
comment|/** @return canonical tag for instruction, e.g., ALOAD for ALOAD_0    */
specifier|public
name|short
name|getCanonicalTag
parameter_list|()
block|{
return|return
name|canon_tag
return|;
block|}
comment|/**    * Returns the type associated with the instruction -     * in case of ALOAD or ASTORE Type.OBJECT is returned.    * This is just a bit incorrect, because ALOAD and ASTORE    * may work on every ReferenceType (including Type.NULL) and    * ASTORE may even work on a ReturnaddressType .    * @return type associated with the instruction    */
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
name|canon_tag
condition|)
block|{
case|case
name|Constants
operator|.
name|ILOAD
case|:
case|case
name|Constants
operator|.
name|ISTORE
case|:
return|return
name|Type
operator|.
name|INT
return|;
case|case
name|Constants
operator|.
name|LLOAD
case|:
case|case
name|Constants
operator|.
name|LSTORE
case|:
return|return
name|Type
operator|.
name|LONG
return|;
case|case
name|Constants
operator|.
name|DLOAD
case|:
case|case
name|Constants
operator|.
name|DSTORE
case|:
return|return
name|Type
operator|.
name|DOUBLE
return|;
case|case
name|Constants
operator|.
name|FLOAD
case|:
case|case
name|Constants
operator|.
name|FSTORE
case|:
return|return
name|Type
operator|.
name|FLOAT
return|;
case|case
name|Constants
operator|.
name|ALOAD
case|:
case|case
name|Constants
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
block|}
end_class

end_unit

