begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|verifier
operator|.
name|structurals
package|;
end_package

begin_comment
comment|/* ====================================================================  * The Apache Software License, Version 1.1  *  * Copyright (c) 2001 The Apache Software Foundation.  All rights  * reserved.  *  * Redistribution and use in source and binary forms, with or without  * modification, are permitted provided that the following conditions  * are met:  *  * 1. Redistributions of source code must retain the above copyright  *    notice, this list of conditions and the following disclaimer.  *  * 2. Redistributions in binary form must reproduce the above copyright  *    notice, this list of conditions and the following disclaimer in  *    the documentation and/or other materials provided with the  *    distribution.  *  * 3. The end-user documentation included with the redistribution,  *    if any, must include the following acknowledgment:  *       "This product includes software developed by the  *        Apache Software Foundation (http://www.apache.org/)."  *    Alternately, this acknowledgment may appear in the software itself,  *    if and wherever such third-party acknowledgments normally appear.  *  * 4. The names "Apache" and "Apache Software Foundation" and  *    "Apache BCEL" must not be used to endorse or promote products  *    derived from this software without prior written permission. For  *    written permission, please contact apache@apache.org.  *  * 5. Products derived from this software may not be called "Apache",  *    "Apache BCEL", nor may "Apache" appear in their name, without  *    prior written permission of the Apache Software Foundation.  *  * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED  * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES  * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE  * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR  * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,  * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT  * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF  * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND  * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,  * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT  * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF  * SUCH DAMAGE.  * ====================================================================  *  * This software consists of voluntary contributions made by many  * individuals on behalf of the Apache Software Foundation.  For more  * information on the Apache Software Foundation, please see  *<http://www.apache.org/>.  */
end_comment

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|bcel
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
name|generic
operator|.
name|*
import|;
end_import

begin_comment
comment|/**  * This class represents an uninitialized object type; see The Java  * Virtual Machine Specification, Second Edition, page 147: 4.9.4 for  * more details.  *  * @version $Id$  * @author<A HREF="http://www.inf.fu-berlin.de/~ehaase"/>Enver Haase</A>  */
end_comment

begin_class
specifier|public
class|class
name|UninitializedObjectType
extends|extends
name|ReferenceType
implements|implements
name|Constants
block|{
comment|/** The "initialized" version. */
specifier|private
name|ObjectType
name|initialized
decl_stmt|;
comment|/** Creates a new instance. */
specifier|public
name|UninitializedObjectType
parameter_list|(
name|ObjectType
name|t
parameter_list|)
block|{
name|super
argument_list|(
name|T_UNKNOWN
argument_list|,
literal|"<UNINITIALIZED OBJECT OF TYPE '"
operator|+
name|t
operator|.
name|getClassName
argument_list|()
operator|+
literal|"'>"
argument_list|)
expr_stmt|;
name|initialized
operator|=
name|t
expr_stmt|;
block|}
comment|/** 	 * Returns the ObjectType of the same class as the one of the uninitialized object 	 * represented by this UninitializedObjectType instance. 	 */
specifier|public
name|ObjectType
name|getInitialized
parameter_list|()
block|{
return|return
name|initialized
return|;
block|}
comment|/** 	 * Returns true on equality of this and o. 	 * Equality means the ObjectType instances of "initialized" 	 * equal one another in this and the o instance. 	 * 	 */
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
if|if
condition|(
operator|!
operator|(
name|o
operator|instanceof
name|UninitializedObjectType
operator|)
condition|)
return|return
literal|false
return|;
return|return
name|initialized
operator|.
name|equals
argument_list|(
operator|(
operator|(
name|UninitializedObjectType
operator|)
name|o
operator|)
operator|.
name|initialized
argument_list|)
return|;
block|}
block|}
end_class

end_unit

