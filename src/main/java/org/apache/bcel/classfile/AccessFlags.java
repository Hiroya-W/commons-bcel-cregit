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
name|classfile
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
comment|/**  * Super class for all objects that have modifiers like private, final, ... I.e. classes, fields, and methods.  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|AccessFlags
block|{
comment|/**      * @deprecated (since 6.0) will be made private; do not access directly, use getter/setter      */
annotation|@
name|java
operator|.
name|lang
operator|.
name|Deprecated
specifier|protected
name|int
name|access_flags
decl_stmt|;
comment|// TODO not used externally at present
specifier|public
name|AccessFlags
parameter_list|()
block|{
block|}
comment|/**      * @param a inital access flags      */
specifier|public
name|AccessFlags
parameter_list|(
specifier|final
name|int
name|a
parameter_list|)
block|{
name|access_flags
operator|=
name|a
expr_stmt|;
block|}
comment|/**      * @return Access flags of the object aka. "modifiers".      */
specifier|public
specifier|final
name|int
name|getAccessFlags
parameter_list|()
block|{
return|return
name|access_flags
return|;
block|}
comment|/**      * @return Access flags of the object aka. "modifiers".      */
specifier|public
specifier|final
name|int
name|getModifiers
parameter_list|()
block|{
return|return
name|access_flags
return|;
block|}
specifier|public
specifier|final
name|boolean
name|isAbstract
parameter_list|()
block|{
return|return
operator|(
name|access_flags
operator|&
name|Const
operator|.
name|ACC_ABSTRACT
operator|)
operator|!=
literal|0
return|;
block|}
specifier|public
specifier|final
name|void
name|isAbstract
parameter_list|(
specifier|final
name|boolean
name|flag
parameter_list|)
block|{
name|setFlag
argument_list|(
name|Const
operator|.
name|ACC_ABSTRACT
argument_list|,
name|flag
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|final
name|boolean
name|isAnnotation
parameter_list|()
block|{
return|return
operator|(
name|access_flags
operator|&
name|Const
operator|.
name|ACC_ANNOTATION
operator|)
operator|!=
literal|0
return|;
block|}
specifier|public
specifier|final
name|void
name|isAnnotation
parameter_list|(
specifier|final
name|boolean
name|flag
parameter_list|)
block|{
name|setFlag
argument_list|(
name|Const
operator|.
name|ACC_ANNOTATION
argument_list|,
name|flag
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|final
name|boolean
name|isEnum
parameter_list|()
block|{
return|return
operator|(
name|access_flags
operator|&
name|Const
operator|.
name|ACC_ENUM
operator|)
operator|!=
literal|0
return|;
block|}
specifier|public
specifier|final
name|void
name|isEnum
parameter_list|(
specifier|final
name|boolean
name|flag
parameter_list|)
block|{
name|setFlag
argument_list|(
name|Const
operator|.
name|ACC_ENUM
argument_list|,
name|flag
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|final
name|boolean
name|isFinal
parameter_list|()
block|{
return|return
operator|(
name|access_flags
operator|&
name|Const
operator|.
name|ACC_FINAL
operator|)
operator|!=
literal|0
return|;
block|}
specifier|public
specifier|final
name|void
name|isFinal
parameter_list|(
specifier|final
name|boolean
name|flag
parameter_list|)
block|{
name|setFlag
argument_list|(
name|Const
operator|.
name|ACC_FINAL
argument_list|,
name|flag
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|final
name|boolean
name|isInterface
parameter_list|()
block|{
return|return
operator|(
name|access_flags
operator|&
name|Const
operator|.
name|ACC_INTERFACE
operator|)
operator|!=
literal|0
return|;
block|}
specifier|public
specifier|final
name|void
name|isInterface
parameter_list|(
specifier|final
name|boolean
name|flag
parameter_list|)
block|{
name|setFlag
argument_list|(
name|Const
operator|.
name|ACC_INTERFACE
argument_list|,
name|flag
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|final
name|boolean
name|isNative
parameter_list|()
block|{
return|return
operator|(
name|access_flags
operator|&
name|Const
operator|.
name|ACC_NATIVE
operator|)
operator|!=
literal|0
return|;
block|}
specifier|public
specifier|final
name|void
name|isNative
parameter_list|(
specifier|final
name|boolean
name|flag
parameter_list|)
block|{
name|setFlag
argument_list|(
name|Const
operator|.
name|ACC_NATIVE
argument_list|,
name|flag
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|final
name|boolean
name|isPrivate
parameter_list|()
block|{
return|return
operator|(
name|access_flags
operator|&
name|Const
operator|.
name|ACC_PRIVATE
operator|)
operator|!=
literal|0
return|;
block|}
specifier|public
specifier|final
name|void
name|isPrivate
parameter_list|(
specifier|final
name|boolean
name|flag
parameter_list|)
block|{
name|setFlag
argument_list|(
name|Const
operator|.
name|ACC_PRIVATE
argument_list|,
name|flag
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|final
name|boolean
name|isProtected
parameter_list|()
block|{
return|return
operator|(
name|access_flags
operator|&
name|Const
operator|.
name|ACC_PROTECTED
operator|)
operator|!=
literal|0
return|;
block|}
specifier|public
specifier|final
name|void
name|isProtected
parameter_list|(
specifier|final
name|boolean
name|flag
parameter_list|)
block|{
name|setFlag
argument_list|(
name|Const
operator|.
name|ACC_PROTECTED
argument_list|,
name|flag
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|final
name|boolean
name|isPublic
parameter_list|()
block|{
return|return
operator|(
name|access_flags
operator|&
name|Const
operator|.
name|ACC_PUBLIC
operator|)
operator|!=
literal|0
return|;
block|}
specifier|public
specifier|final
name|void
name|isPublic
parameter_list|(
specifier|final
name|boolean
name|flag
parameter_list|)
block|{
name|setFlag
argument_list|(
name|Const
operator|.
name|ACC_PUBLIC
argument_list|,
name|flag
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|final
name|boolean
name|isStatic
parameter_list|()
block|{
return|return
operator|(
name|access_flags
operator|&
name|Const
operator|.
name|ACC_STATIC
operator|)
operator|!=
literal|0
return|;
block|}
specifier|public
specifier|final
name|void
name|isStatic
parameter_list|(
specifier|final
name|boolean
name|flag
parameter_list|)
block|{
name|setFlag
argument_list|(
name|Const
operator|.
name|ACC_STATIC
argument_list|,
name|flag
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|final
name|boolean
name|isStrictfp
parameter_list|()
block|{
return|return
operator|(
name|access_flags
operator|&
name|Const
operator|.
name|ACC_STRICT
operator|)
operator|!=
literal|0
return|;
block|}
specifier|public
specifier|final
name|void
name|isStrictfp
parameter_list|(
specifier|final
name|boolean
name|flag
parameter_list|)
block|{
name|setFlag
argument_list|(
name|Const
operator|.
name|ACC_STRICT
argument_list|,
name|flag
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|final
name|boolean
name|isSynchronized
parameter_list|()
block|{
return|return
operator|(
name|access_flags
operator|&
name|Const
operator|.
name|ACC_SYNCHRONIZED
operator|)
operator|!=
literal|0
return|;
block|}
specifier|public
specifier|final
name|void
name|isSynchronized
parameter_list|(
specifier|final
name|boolean
name|flag
parameter_list|)
block|{
name|setFlag
argument_list|(
name|Const
operator|.
name|ACC_SYNCHRONIZED
argument_list|,
name|flag
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|final
name|boolean
name|isSynthetic
parameter_list|()
block|{
return|return
operator|(
name|access_flags
operator|&
name|Const
operator|.
name|ACC_SYNTHETIC
operator|)
operator|!=
literal|0
return|;
block|}
specifier|public
specifier|final
name|void
name|isSynthetic
parameter_list|(
specifier|final
name|boolean
name|flag
parameter_list|)
block|{
name|setFlag
argument_list|(
name|Const
operator|.
name|ACC_SYNTHETIC
argument_list|,
name|flag
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|final
name|boolean
name|isTransient
parameter_list|()
block|{
return|return
operator|(
name|access_flags
operator|&
name|Const
operator|.
name|ACC_TRANSIENT
operator|)
operator|!=
literal|0
return|;
block|}
specifier|public
specifier|final
name|void
name|isTransient
parameter_list|(
specifier|final
name|boolean
name|flag
parameter_list|)
block|{
name|setFlag
argument_list|(
name|Const
operator|.
name|ACC_TRANSIENT
argument_list|,
name|flag
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|final
name|boolean
name|isVarArgs
parameter_list|()
block|{
return|return
operator|(
name|access_flags
operator|&
name|Const
operator|.
name|ACC_VARARGS
operator|)
operator|!=
literal|0
return|;
block|}
specifier|public
specifier|final
name|void
name|isVarArgs
parameter_list|(
specifier|final
name|boolean
name|flag
parameter_list|)
block|{
name|setFlag
argument_list|(
name|Const
operator|.
name|ACC_VARARGS
argument_list|,
name|flag
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|final
name|boolean
name|isVolatile
parameter_list|()
block|{
return|return
operator|(
name|access_flags
operator|&
name|Const
operator|.
name|ACC_VOLATILE
operator|)
operator|!=
literal|0
return|;
block|}
specifier|public
specifier|final
name|void
name|isVolatile
parameter_list|(
specifier|final
name|boolean
name|flag
parameter_list|)
block|{
name|setFlag
argument_list|(
name|Const
operator|.
name|ACC_VOLATILE
argument_list|,
name|flag
argument_list|)
expr_stmt|;
block|}
comment|/**      * Set access flags aka "modifiers".      *      * @param accessFlags Access flags of the object.      */
specifier|public
specifier|final
name|void
name|setAccessFlags
parameter_list|(
specifier|final
name|int
name|accessFlags
parameter_list|)
block|{
name|this
operator|.
name|access_flags
operator|=
name|accessFlags
expr_stmt|;
block|}
specifier|private
name|void
name|setFlag
parameter_list|(
specifier|final
name|int
name|flag
parameter_list|,
specifier|final
name|boolean
name|set
parameter_list|)
block|{
if|if
condition|(
operator|(
name|access_flags
operator|&
name|flag
operator|)
operator|!=
literal|0
condition|)
block|{
comment|// Flag is set already
if|if
condition|(
operator|!
name|set
condition|)
block|{
name|access_flags
operator|^=
name|flag
expr_stmt|;
block|}
block|}
if|else if
condition|(
name|set
condition|)
block|{
name|access_flags
operator||=
name|flag
expr_stmt|;
block|}
block|}
comment|/**      * Set access flags aka "modifiers".      *      * @param accessFlags Access flags of the object.      */
specifier|public
specifier|final
name|void
name|setModifiers
parameter_list|(
specifier|final
name|int
name|accessFlags
parameter_list|)
block|{
name|setAccessFlags
argument_list|(
name|accessFlags
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

