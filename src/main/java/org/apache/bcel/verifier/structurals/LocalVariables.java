begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Copyright  2000-2004 The Apache Software Foundation  *  *  Licensed under the Apache License, Version 2.0 (the "License");   *  you may not use this file except in compliance with the License.  *  You may obtain a copy of the License at  *  *      http://www.apache.org/licenses/LICENSE-2.0  *  *  Unless required by applicable law or agreed to in writing, software  *  distributed under the License is distributed on an "AS IS" BASIS,  *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *  See the License for the specific language governing permissions and  *  limitations under the License.   *  */
end_comment

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
name|ReferenceType
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

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|verifier
operator|.
name|exc
operator|.
name|AssertionViolatedException
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
name|verifier
operator|.
name|exc
operator|.
name|StructuralCodeConstraintException
import|;
end_import

begin_comment
comment|/**  * This class implements an array of local variables used for symbolic JVM  * simulation.  *  * @version $Id$  * @author Enver Haase  */
end_comment

begin_class
specifier|public
class|class
name|LocalVariables
block|{
comment|/** The Type[] containing the local variable slots. */
specifier|private
name|Type
index|[]
name|locals
decl_stmt|;
comment|/** 	 * Creates a new LocalVariables object. 	 */
specifier|public
name|LocalVariables
parameter_list|(
name|int
name|maxLocals
parameter_list|)
block|{
name|locals
operator|=
operator|new
name|Type
index|[
name|maxLocals
index|]
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
name|maxLocals
condition|;
name|i
operator|++
control|)
block|{
name|locals
index|[
name|i
index|]
operator|=
name|Type
operator|.
name|UNKNOWN
expr_stmt|;
block|}
block|}
comment|/** 	 * Returns a deep copy of this object; i.e. the clone 	 * operates on a new local variable array. 	 * However, the Type objects in the array are shared. 	 */
specifier|protected
name|Object
name|clone
parameter_list|()
block|{
name|LocalVariables
name|lvs
init|=
operator|new
name|LocalVariables
argument_list|(
name|locals
operator|.
name|length
argument_list|)
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
name|locals
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|lvs
operator|.
name|locals
index|[
name|i
index|]
operator|=
name|this
operator|.
name|locals
index|[
name|i
index|]
expr_stmt|;
block|}
return|return
name|lvs
return|;
block|}
comment|/** 	 * Returns the type of the local variable slot i. 	 */
specifier|public
name|Type
name|get
parameter_list|(
name|int
name|i
parameter_list|)
block|{
return|return
name|locals
index|[
name|i
index|]
return|;
block|}
comment|/** 	 * Returns a (correctly typed) clone of this object. 	 * This is equivalent to ((LocalVariables) this.clone()). 	 */
specifier|public
name|LocalVariables
name|getClone
parameter_list|()
block|{
return|return
operator|(
name|LocalVariables
operator|)
name|this
operator|.
name|clone
argument_list|()
return|;
block|}
comment|/** 	 * Returns the number of local variable slots this 	 * LocalVariables instance has. 	 */
specifier|public
name|int
name|maxLocals
parameter_list|()
block|{
return|return
name|locals
operator|.
name|length
return|;
block|}
comment|/** 	 * Sets a new Type for the given local variable slot. 	 */
specifier|public
name|void
name|set
parameter_list|(
name|int
name|i
parameter_list|,
name|Type
name|type
parameter_list|)
block|{
if|if
condition|(
name|type
operator|==
name|Type
operator|.
name|BYTE
operator|||
name|type
operator|==
name|Type
operator|.
name|SHORT
operator|||
name|type
operator|==
name|Type
operator|.
name|BOOLEAN
operator|||
name|type
operator|==
name|Type
operator|.
name|CHAR
condition|)
block|{
throw|throw
operator|new
name|AssertionViolatedException
argument_list|(
literal|"LocalVariables do not know about '"
operator|+
name|type
operator|+
literal|"'. Use Type.INT instead."
argument_list|)
throw|;
block|}
name|locals
index|[
name|i
index|]
operator|=
name|type
expr_stmt|;
block|}
comment|/** @return a hash code value for the object.      */
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|locals
operator|.
name|length
return|;
block|}
comment|/* 	 * Fulfills the general contract of Object.equals(). 	 */
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
name|LocalVariables
operator|)
condition|)
block|{
return|return
literal|false
return|;
block|}
name|LocalVariables
name|lv
init|=
operator|(
name|LocalVariables
operator|)
name|o
decl_stmt|;
if|if
condition|(
name|this
operator|.
name|locals
operator|.
name|length
operator|!=
name|lv
operator|.
name|locals
operator|.
name|length
condition|)
block|{
return|return
literal|false
return|;
block|}
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|this
operator|.
name|locals
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
operator|!
name|this
operator|.
name|locals
index|[
name|i
index|]
operator|.
name|equals
argument_list|(
name|lv
operator|.
name|locals
index|[
name|i
index|]
argument_list|)
condition|)
block|{
comment|//System.out.println(this.locals[i]+" is not "+lv.locals[i]);
return|return
literal|false
return|;
block|}
block|}
return|return
literal|true
return|;
block|}
comment|/** 	 * Merges two local variables sets as described in the Java Virtual Machine Specification, 	 * Second Edition, section 4.9.2, page 146. 	 */
specifier|public
name|void
name|merge
parameter_list|(
name|LocalVariables
name|lv
parameter_list|)
block|{
if|if
condition|(
name|this
operator|.
name|locals
operator|.
name|length
operator|!=
name|lv
operator|.
name|locals
operator|.
name|length
condition|)
block|{
throw|throw
operator|new
name|AssertionViolatedException
argument_list|(
literal|"Merging LocalVariables of different size?!? From different methods or what?!?"
argument_list|)
throw|;
block|}
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|locals
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|merge
argument_list|(
name|lv
argument_list|,
name|i
argument_list|)
expr_stmt|;
block|}
block|}
comment|/** 	 * Merges a single local variable. 	 * 	 * @see #merge(LocalVariables) 	 */
specifier|private
name|void
name|merge
parameter_list|(
name|LocalVariables
name|lv
parameter_list|,
name|int
name|i
parameter_list|)
block|{
try|try
block|{
comment|// We won't accept an unitialized object if we know it was initialized;
comment|// compare vmspec2, 4.9.4, last paragraph.
if|if
condition|(
operator|(
operator|!
operator|(
name|locals
index|[
name|i
index|]
operator|instanceof
name|UninitializedObjectType
operator|)
operator|)
operator|&&
operator|(
name|lv
operator|.
name|locals
index|[
name|i
index|]
operator|instanceof
name|UninitializedObjectType
operator|)
condition|)
block|{
throw|throw
operator|new
name|StructuralCodeConstraintException
argument_list|(
literal|"Backwards branch with an uninitialized object in the local variables detected."
argument_list|)
throw|;
block|}
comment|// Even harder, what about _different_ uninitialized object types?!
if|if
condition|(
operator|(
operator|!
operator|(
name|locals
index|[
name|i
index|]
operator|.
name|equals
argument_list|(
name|lv
operator|.
name|locals
index|[
name|i
index|]
argument_list|)
operator|)
operator|)
operator|&&
operator|(
name|locals
index|[
name|i
index|]
operator|instanceof
name|UninitializedObjectType
operator|)
operator|&&
operator|(
name|lv
operator|.
name|locals
index|[
name|i
index|]
operator|instanceof
name|UninitializedObjectType
operator|)
condition|)
block|{
throw|throw
operator|new
name|StructuralCodeConstraintException
argument_list|(
literal|"Backwards branch with an uninitialized object in the local variables detected."
argument_list|)
throw|;
block|}
comment|// If we just didn't know that it was initialized, we have now learned.
if|if
condition|(
name|locals
index|[
name|i
index|]
operator|instanceof
name|UninitializedObjectType
condition|)
block|{
if|if
condition|(
operator|!
operator|(
name|lv
operator|.
name|locals
index|[
name|i
index|]
operator|instanceof
name|UninitializedObjectType
operator|)
condition|)
block|{
name|locals
index|[
name|i
index|]
operator|=
operator|(
operator|(
name|UninitializedObjectType
operator|)
name|locals
index|[
name|i
index|]
operator|)
operator|.
name|getInitialized
argument_list|()
expr_stmt|;
block|}
block|}
if|if
condition|(
operator|(
name|locals
index|[
name|i
index|]
operator|instanceof
name|ReferenceType
operator|)
operator|&&
operator|(
name|lv
operator|.
name|locals
index|[
name|i
index|]
operator|instanceof
name|ReferenceType
operator|)
condition|)
block|{
if|if
condition|(
operator|!
name|locals
index|[
name|i
index|]
operator|.
name|equals
argument_list|(
name|lv
operator|.
name|locals
index|[
name|i
index|]
argument_list|)
condition|)
block|{
comment|// needed in case of two UninitializedObjectType instances
name|Type
name|sup
init|=
operator|(
operator|(
name|ReferenceType
operator|)
name|locals
index|[
name|i
index|]
operator|)
operator|.
name|getFirstCommonSuperclass
argument_list|(
operator|(
name|ReferenceType
operator|)
operator|(
name|lv
operator|.
name|locals
index|[
name|i
index|]
operator|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|sup
operator|!=
literal|null
condition|)
block|{
name|locals
index|[
name|i
index|]
operator|=
name|sup
expr_stmt|;
block|}
else|else
block|{
comment|// We should have checked this in Pass2!
throw|throw
operator|new
name|AssertionViolatedException
argument_list|(
literal|"Could not load all the super classes of '"
operator|+
name|locals
index|[
name|i
index|]
operator|+
literal|"' and '"
operator|+
name|lv
operator|.
name|locals
index|[
name|i
index|]
operator|+
literal|"'."
argument_list|)
throw|;
block|}
block|}
block|}
else|else
block|{
if|if
condition|(
operator|!
operator|(
name|locals
index|[
name|i
index|]
operator|.
name|equals
argument_list|(
name|lv
operator|.
name|locals
index|[
name|i
index|]
argument_list|)
operator|)
condition|)
block|{
comment|/*TODO 				if ((locals[i] instanceof org.apache.bcel.generic.ReturnaddressType)&& (lv.locals[i] instanceof org.apache.bcel.generic.ReturnaddressType)){ 					//System.err.println("merging "+locals[i]+" and "+lv.locals[i]); 					throw new AssertionViolatedException("Merging different ReturnAddresses: '"+locals[i]+"' and '"+lv.locals[i]+"'."); 				} */
name|locals
index|[
name|i
index|]
operator|=
name|Type
operator|.
name|UNKNOWN
expr_stmt|;
block|}
block|}
block|}
catch|catch
parameter_list|(
name|ClassNotFoundException
name|e
parameter_list|)
block|{
comment|// FIXME: maybe not the best way to handle this
throw|throw
operator|new
name|AssertionViolatedException
argument_list|(
literal|"Missing class: "
operator|+
name|e
operator|.
name|toString
argument_list|()
argument_list|)
throw|;
block|}
block|}
comment|/** 	 * Returns a String representation of this object. 	 */
specifier|public
name|String
name|toString
parameter_list|()
block|{
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
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
name|locals
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|Integer
operator|.
name|toString
argument_list|(
name|i
argument_list|)
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|": "
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|locals
index|[
name|i
index|]
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"\n"
argument_list|)
expr_stmt|;
block|}
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
comment|/** 	 * Replaces all occurences of u in this local variables set 	 * with an "initialized" ObjectType. 	 */
specifier|public
name|void
name|initializeObject
parameter_list|(
name|UninitializedObjectType
name|u
parameter_list|)
block|{
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|locals
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|locals
index|[
name|i
index|]
operator|==
name|u
condition|)
block|{
name|locals
index|[
name|i
index|]
operator|=
name|u
operator|.
name|getInitialized
argument_list|()
expr_stmt|;
block|}
block|}
block|}
block|}
end_class

end_unit
