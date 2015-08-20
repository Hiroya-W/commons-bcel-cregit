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
name|commons
operator|.
name|bcel6
operator|.
name|Constants
import|;
end_import

begin_comment
comment|/**  * This class represents an entry in the exception table of the<em>Code</em>  * attribute and is used only there. It contains a range in which a  * particular exception handler is active.  *  * @version $Id$  * @see     Code  */
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
block|{
specifier|private
name|int
name|start_pc
decl_stmt|;
comment|// Range in the code the exception handler is
specifier|private
name|int
name|end_pc
decl_stmt|;
comment|// active. start_pc is inclusive, end_pc exclusive
specifier|private
name|int
name|handler_pc
decl_stmt|;
comment|/* Starting address of exception handler, i.e.,      * an offset from start of code.      */
specifier|private
name|int
name|catch_type
decl_stmt|;
comment|/* If this is zero the handler catches any      * exception, otherwise it points to the      * exception class which is to be caught.      */
comment|/**      * Initialize from another object.      */
specifier|public
name|CodeException
parameter_list|(
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
comment|/**      * @param start_pc Range in the code the exception handler is active,      * start_pc is inclusive while      * @param end_pc is exclusive      * @param handler_pc Starting address of exception handler, i.e.,      * an offset from start of code.      * @param catch_type If zero the handler catches any       * exception, otherwise it points to the exception class which is       * to be caught.      */
specifier|public
name|CodeException
parameter_list|(
name|int
name|start_pc
parameter_list|,
name|int
name|end_pc
parameter_list|,
name|int
name|handler_pc
parameter_list|,
name|int
name|catch_type
parameter_list|)
block|{
name|this
operator|.
name|start_pc
operator|=
name|start_pc
expr_stmt|;
name|this
operator|.
name|end_pc
operator|=
name|end_pc
expr_stmt|;
name|this
operator|.
name|handler_pc
operator|=
name|handler_pc
expr_stmt|;
name|this
operator|.
name|catch_type
operator|=
name|catch_type
expr_stmt|;
block|}
comment|/**      * Called by objects that are traversing the nodes of the tree implicitely      * defined by the contents of a Java class. I.e., the hierarchy of methods,      * fields, attributes, etc. spawns a tree of objects.      *      * @param v Visitor object      */
annotation|@
name|Override
specifier|public
name|void
name|accept
parameter_list|(
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
specifier|final
name|void
name|dump
parameter_list|(
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
name|start_pc
argument_list|)
expr_stmt|;
name|file
operator|.
name|writeShort
argument_list|(
name|end_pc
argument_list|)
expr_stmt|;
name|file
operator|.
name|writeShort
argument_list|(
name|handler_pc
argument_list|)
expr_stmt|;
name|file
operator|.
name|writeShort
argument_list|(
name|catch_type
argument_list|)
expr_stmt|;
block|}
comment|/**      * @return 0, if the handler catches any exception, otherwise it points to      * the exception class which is to be caught.      */
specifier|public
specifier|final
name|int
name|getCatchType
parameter_list|()
block|{
return|return
name|catch_type
return|;
block|}
comment|/**      * @return Exclusive end index of the region where the handler is active.      */
specifier|public
specifier|final
name|int
name|getEndPC
parameter_list|()
block|{
return|return
name|end_pc
return|;
block|}
comment|/**      * @return Starting address of exception handler, relative to the code.      */
specifier|public
specifier|final
name|int
name|getHandlerPC
parameter_list|()
block|{
return|return
name|handler_pc
return|;
block|}
comment|/**      * @return Inclusive start index of the region where the handler is active.      */
specifier|public
specifier|final
name|int
name|getStartPC
parameter_list|()
block|{
return|return
name|start_pc
return|;
block|}
comment|/**      * @param catch_type the type of exception that is caught      */
specifier|public
specifier|final
name|void
name|setCatchType
parameter_list|(
name|int
name|catch_type
parameter_list|)
block|{
name|this
operator|.
name|catch_type
operator|=
name|catch_type
expr_stmt|;
block|}
comment|/**      * @param end_pc end of handled block      */
specifier|public
specifier|final
name|void
name|setEndPC
parameter_list|(
name|int
name|end_pc
parameter_list|)
block|{
name|this
operator|.
name|end_pc
operator|=
name|end_pc
expr_stmt|;
block|}
comment|/**      * @param handler_pc where the actual code is      */
specifier|public
specifier|final
name|void
name|setHandlerPC
parameter_list|(
name|int
name|handler_pc
parameter_list|)
block|{
comment|// TODO unused
name|this
operator|.
name|handler_pc
operator|=
name|handler_pc
expr_stmt|;
block|}
comment|/**      * @param start_pc start of handled block      */
specifier|public
specifier|final
name|void
name|setStartPC
parameter_list|(
name|int
name|start_pc
parameter_list|)
block|{
comment|// TODO unused
name|this
operator|.
name|start_pc
operator|=
name|start_pc
expr_stmt|;
block|}
comment|/**      * @return String representation.      */
annotation|@
name|Override
specifier|public
specifier|final
name|String
name|toString
parameter_list|()
block|{
return|return
literal|"CodeException(start_pc = "
operator|+
name|start_pc
operator|+
literal|", end_pc = "
operator|+
name|end_pc
operator|+
literal|", handler_pc = "
operator|+
name|handler_pc
operator|+
literal|", catch_type = "
operator|+
name|catch_type
operator|+
literal|")"
return|;
block|}
comment|/**      * @return String representation.      */
specifier|public
specifier|final
name|String
name|toString
parameter_list|(
name|ConstantPool
name|cp
parameter_list|,
name|boolean
name|verbose
parameter_list|)
block|{
name|String
name|str
decl_stmt|;
if|if
condition|(
name|catch_type
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
name|catch_type
argument_list|,
name|Constants
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
name|catch_type
operator|+
literal|")"
else|:
literal|""
operator|)
expr_stmt|;
block|}
return|return
name|start_pc
operator|+
literal|"\t"
operator|+
name|end_pc
operator|+
literal|"\t"
operator|+
name|handler_pc
operator|+
literal|"\t"
operator|+
name|str
return|;
block|}
specifier|public
specifier|final
name|String
name|toString
parameter_list|(
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

