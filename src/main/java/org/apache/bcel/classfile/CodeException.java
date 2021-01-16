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
name|classfile
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|DataInput
import|;
end_import

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
name|Constants
import|;
end_import

begin_comment
comment|/**  * This class represents an entry in the exception table of the<em>Code</em>  * attribute and is used only there. It contains a range in which a  * particular exception handler is active.  *  * @see     Code  */
end_comment

begin_class
specifier|public
specifier|final
class|class
name|CodeException
implements|implements
name|Cloneable
implements|,
name|Node
implements|,
name|Constants
block|{
specifier|private
name|int
name|startPc
decl_stmt|;
comment|// Range in the code the exception handler is
specifier|private
name|int
name|endPc
decl_stmt|;
comment|// active. startPc is inclusive, endPc exclusive
specifier|private
name|int
name|handlerPc
decl_stmt|;
comment|/* Starting address of exception handler, i.e.,      * an offset from start of code.      */
specifier|private
name|int
name|catchType
decl_stmt|;
comment|/* If this is zero the handler catches any      * exception, otherwise it points to the      * exception class which is to be caught.      */
comment|/**      * Empty array.      */
specifier|static
specifier|final
name|CodeException
index|[]
name|EMPTY_CODE_EXCEPTION_ARRAY
init|=
operator|new
name|CodeException
index|[
literal|0
index|]
decl_stmt|;
comment|/**      * Initialize from another object.      */
specifier|public
name|CodeException
parameter_list|(
specifier|final
name|CodeException
name|c
parameter_list|)
block|{
name|this
argument_list|(
name|c
operator|.
name|getStartPC
argument_list|()
argument_list|,
name|c
operator|.
name|getEndPC
argument_list|()
argument_list|,
name|c
operator|.
name|getHandlerPC
argument_list|()
argument_list|,
name|c
operator|.
name|getCatchType
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**      * Construct object from file stream.      * @param file Input stream      * @throws IOException      */
name|CodeException
parameter_list|(
specifier|final
name|DataInput
name|file
parameter_list|)
throws|throws
name|IOException
block|{
name|this
argument_list|(
name|file
operator|.
name|readUnsignedShort
argument_list|()
argument_list|,
name|file
operator|.
name|readUnsignedShort
argument_list|()
argument_list|,
name|file
operator|.
name|readUnsignedShort
argument_list|()
argument_list|,
name|file
operator|.
name|readUnsignedShort
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**      * @param startPc Range in the code the exception handler is active,      * startPc is inclusive while      * @param endPc is exclusive      * @param handlerPc Starting address of exception handler, i.e.,      * an offset from start of code.      * @param catchType If zero the handler catches any      * exception, otherwise it points to the exception class which is      * to be caught.      */
specifier|public
name|CodeException
parameter_list|(
specifier|final
name|int
name|startPc
parameter_list|,
specifier|final
name|int
name|endPc
parameter_list|,
specifier|final
name|int
name|handlerPc
parameter_list|,
specifier|final
name|int
name|catchType
parameter_list|)
block|{
name|this
operator|.
name|startPc
operator|=
name|startPc
expr_stmt|;
name|this
operator|.
name|endPc
operator|=
name|endPc
expr_stmt|;
name|this
operator|.
name|handlerPc
operator|=
name|handlerPc
expr_stmt|;
name|this
operator|.
name|catchType
operator|=
name|catchType
expr_stmt|;
block|}
comment|/**      * Called by objects that are traversing the nodes of the tree implicitely      * defined by the contents of a Java class. I.e., the hierarchy of methods,      * fields, attributes, etc. spawns a tree of objects.      *      * @param v Visitor object      */
annotation|@
name|Override
specifier|public
name|void
name|accept
parameter_list|(
specifier|final
name|Visitor
name|v
parameter_list|)
block|{
name|v
operator|.
name|visitCodeException
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
comment|/**      * Dump code exception to file stream in binary format.      *      * @param file Output file stream      * @throws IOException      */
specifier|public
name|void
name|dump
parameter_list|(
specifier|final
name|DataOutputStream
name|file
parameter_list|)
throws|throws
name|IOException
block|{
name|file
operator|.
name|writeShort
argument_list|(
name|startPc
argument_list|)
expr_stmt|;
name|file
operator|.
name|writeShort
argument_list|(
name|endPc
argument_list|)
expr_stmt|;
name|file
operator|.
name|writeShort
argument_list|(
name|handlerPc
argument_list|)
expr_stmt|;
name|file
operator|.
name|writeShort
argument_list|(
name|catchType
argument_list|)
expr_stmt|;
block|}
comment|/**      * @return 0, if the handler catches any exception, otherwise it points to      * the exception class which is to be caught.      */
specifier|public
name|int
name|getCatchType
parameter_list|()
block|{
return|return
name|catchType
return|;
block|}
comment|/**      * @return Exclusive end index of the region where the handler is active.      */
specifier|public
name|int
name|getEndPC
parameter_list|()
block|{
return|return
name|endPc
return|;
block|}
comment|/**      * @return Starting address of exception handler, relative to the code.      */
specifier|public
name|int
name|getHandlerPC
parameter_list|()
block|{
return|return
name|handlerPc
return|;
block|}
comment|/**      * @return Inclusive start index of the region where the handler is active.      */
specifier|public
name|int
name|getStartPC
parameter_list|()
block|{
return|return
name|startPc
return|;
block|}
comment|/**      * @param catchType the type of exception that is caught      */
specifier|public
name|void
name|setCatchType
parameter_list|(
specifier|final
name|int
name|catchType
parameter_list|)
block|{
name|this
operator|.
name|catchType
operator|=
name|catchType
expr_stmt|;
block|}
comment|/**      * @param endPc end of handled block      */
specifier|public
name|void
name|setEndPC
parameter_list|(
specifier|final
name|int
name|endPc
parameter_list|)
block|{
name|this
operator|.
name|endPc
operator|=
name|endPc
expr_stmt|;
block|}
comment|/**      * @param handlerPc where the actual code is      */
specifier|public
name|void
name|setHandlerPC
parameter_list|(
specifier|final
name|int
name|handlerPc
parameter_list|)
block|{
comment|// TODO unused
name|this
operator|.
name|handlerPc
operator|=
name|handlerPc
expr_stmt|;
block|}
comment|/**      * @param startPc start of handled block      */
specifier|public
name|void
name|setStartPC
parameter_list|(
specifier|final
name|int
name|startPc
parameter_list|)
block|{
comment|// TODO unused
name|this
operator|.
name|startPc
operator|=
name|startPc
expr_stmt|;
block|}
comment|/**      * @return String representation.      */
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
literal|"CodeException(startPc = "
operator|+
name|startPc
operator|+
literal|", endPc = "
operator|+
name|endPc
operator|+
literal|", handlerPc = "
operator|+
name|handlerPc
operator|+
literal|", catchType = "
operator|+
name|catchType
operator|+
literal|")"
return|;
block|}
comment|/**      * @return String representation.      */
specifier|public
name|String
name|toString
parameter_list|(
specifier|final
name|ConstantPool
name|cp
parameter_list|,
specifier|final
name|boolean
name|verbose
parameter_list|)
block|{
name|String
name|str
decl_stmt|;
if|if
condition|(
name|catchType
operator|==
literal|0
condition|)
block|{
name|str
operator|=
literal|"<Any exception>(0)"
expr_stmt|;
block|}
else|else
block|{
name|str
operator|=
name|Utility
operator|.
name|compactClassName
argument_list|(
name|cp
operator|.
name|getConstantString
argument_list|(
name|catchType
argument_list|,
name|Const
operator|.
name|CONSTANT_Class
argument_list|)
argument_list|,
literal|false
argument_list|)
operator|+
operator|(
name|verbose
condition|?
literal|"("
operator|+
name|catchType
operator|+
literal|")"
else|:
literal|""
operator|)
expr_stmt|;
block|}
return|return
name|startPc
operator|+
literal|"\t"
operator|+
name|endPc
operator|+
literal|"\t"
operator|+
name|handlerPc
operator|+
literal|"\t"
operator|+
name|str
return|;
block|}
specifier|public
name|String
name|toString
parameter_list|(
specifier|final
name|ConstantPool
name|cp
parameter_list|)
block|{
return|return
name|toString
argument_list|(
name|cp
argument_list|,
literal|true
argument_list|)
return|;
block|}
comment|/**      * @return deep copy of this object      */
specifier|public
name|CodeException
name|copy
parameter_list|()
block|{
try|try
block|{
return|return
operator|(
name|CodeException
operator|)
name|clone
argument_list|()
return|;
block|}
catch|catch
parameter_list|(
specifier|final
name|CloneNotSupportedException
name|e
parameter_list|)
block|{
comment|// TODO should this throw?
block|}
return|return
literal|null
return|;
block|}
block|}
end_class

end_unit

