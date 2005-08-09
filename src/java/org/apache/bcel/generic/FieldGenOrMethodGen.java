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
name|List
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
name|AccessFlags
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
name|Attribute
import|;
end_import

begin_comment
comment|/**  * Super class for FieldGen and MethodGen objects, since they have  * some methods in common!  *  * @version $Id$  * @author<A HREF="mailto:m.dahm@gmx.de">M. Dahm</A>  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|FieldGenOrMethodGen
extends|extends
name|AccessFlags
implements|implements
name|NamedAndTyped
implements|,
name|Cloneable
block|{
specifier|protected
name|String
name|name
decl_stmt|;
specifier|protected
name|Type
name|type
decl_stmt|;
specifier|protected
name|ConstantPoolGen
name|cp
decl_stmt|;
specifier|private
name|List
name|attribute_vec
init|=
operator|new
name|ArrayList
argument_list|()
decl_stmt|;
specifier|protected
name|FieldGenOrMethodGen
parameter_list|()
block|{
block|}
specifier|public
name|void
name|setType
parameter_list|(
name|Type
name|type
parameter_list|)
block|{
if|if
condition|(
name|type
operator|.
name|getType
argument_list|()
operator|==
name|Constants
operator|.
name|T_ADDRESS
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Type can not be "
operator|+
name|type
argument_list|)
throw|;
name|this
operator|.
name|type
operator|=
name|type
expr_stmt|;
block|}
specifier|public
name|Type
name|getType
parameter_list|()
block|{
return|return
name|type
return|;
block|}
comment|/** @return name of method/field.    */
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|name
return|;
block|}
specifier|public
name|void
name|setName
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|this
operator|.
name|name
operator|=
name|name
expr_stmt|;
block|}
specifier|public
name|ConstantPoolGen
name|getConstantPool
parameter_list|()
block|{
return|return
name|cp
return|;
block|}
specifier|public
name|void
name|setConstantPool
parameter_list|(
name|ConstantPoolGen
name|cp
parameter_list|)
block|{
name|this
operator|.
name|cp
operator|=
name|cp
expr_stmt|;
block|}
comment|/**    * Add an attribute to this method. Currently, the JVM knows about    * the `Code', `ConstantValue', `Synthetic' and `Exceptions'    * attributes. Other attributes will be ignored by the JVM but do no    * harm.    *    * @param a attribute to be added    */
specifier|public
name|void
name|addAttribute
parameter_list|(
name|Attribute
name|a
parameter_list|)
block|{
name|attribute_vec
operator|.
name|add
argument_list|(
name|a
argument_list|)
expr_stmt|;
block|}
comment|/**    * Remove an attribute.    */
specifier|public
name|void
name|removeAttribute
parameter_list|(
name|Attribute
name|a
parameter_list|)
block|{
name|attribute_vec
operator|.
name|remove
argument_list|(
name|a
argument_list|)
expr_stmt|;
block|}
comment|/**    * Remove all attributes.    */
specifier|public
name|void
name|removeAttributes
parameter_list|()
block|{
name|attribute_vec
operator|.
name|clear
argument_list|()
expr_stmt|;
block|}
comment|/**    * @return all attributes of this method.    */
specifier|public
name|Attribute
index|[]
name|getAttributes
parameter_list|()
block|{
name|Attribute
index|[]
name|attributes
init|=
operator|new
name|Attribute
index|[
name|attribute_vec
operator|.
name|size
argument_list|()
index|]
decl_stmt|;
name|attribute_vec
operator|.
name|toArray
argument_list|(
name|attributes
argument_list|)
expr_stmt|;
return|return
name|attributes
return|;
block|}
comment|/** @return signature of method/field.    */
specifier|public
specifier|abstract
name|String
name|getSignature
parameter_list|()
function_decl|;
specifier|public
name|Object
name|clone
parameter_list|()
block|{
try|try
block|{
return|return
name|super
operator|.
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
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
name|e
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
block|}
end_class

end_unit

