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
name|io
operator|.
name|DataInputStream
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
name|classfile
operator|.
name|AnnotationElementValue
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
name|AnnotationEntry
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
name|ArrayElementValue
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
name|ClassElementValue
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
name|ElementValue
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
name|EnumElementValue
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
name|SimpleElementValue
import|;
end_import

begin_class
specifier|public
specifier|abstract
class|class
name|ElementValueGen
block|{
specifier|protected
name|int
name|type
decl_stmt|;
specifier|protected
name|ConstantPoolGen
name|cpGen
decl_stmt|;
specifier|protected
name|ElementValueGen
parameter_list|(
name|int
name|type
parameter_list|,
name|ConstantPoolGen
name|cpGen
parameter_list|)
block|{
name|this
operator|.
name|type
operator|=
name|type
expr_stmt|;
name|this
operator|.
name|cpGen
operator|=
name|cpGen
expr_stmt|;
block|}
comment|/** 	 * Subtypes return an immutable variant of the ElementValueGen 	 */
specifier|public
specifier|abstract
name|ElementValue
name|getElementValue
parameter_list|()
function_decl|;
specifier|public
name|int
name|getElementValueType
parameter_list|()
block|{
return|return
name|type
return|;
block|}
specifier|public
specifier|abstract
name|String
name|stringifyValue
parameter_list|()
function_decl|;
specifier|public
specifier|abstract
name|void
name|dump
parameter_list|(
name|DataOutputStream
name|dos
parameter_list|)
throws|throws
name|IOException
function_decl|;
specifier|public
specifier|static
specifier|final
name|int
name|STRING
init|=
literal|'s'
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|ENUM_CONSTANT
init|=
literal|'e'
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|CLASS
init|=
literal|'c'
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|ANNOTATION
init|=
literal|'@'
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|ARRAY
init|=
literal|'['
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|PRIMITIVE_INT
init|=
literal|'I'
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|PRIMITIVE_BYTE
init|=
literal|'B'
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|PRIMITIVE_CHAR
init|=
literal|'C'
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|PRIMITIVE_DOUBLE
init|=
literal|'D'
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|PRIMITIVE_FLOAT
init|=
literal|'F'
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|PRIMITIVE_LONG
init|=
literal|'J'
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|PRIMITIVE_SHORT
init|=
literal|'S'
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|PRIMITIVE_BOOLEAN
init|=
literal|'Z'
decl_stmt|;
specifier|public
specifier|static
name|ElementValueGen
name|readElementValue
parameter_list|(
name|DataInputStream
name|dis
parameter_list|,
name|ConstantPoolGen
name|cpGen
parameter_list|)
throws|throws
name|IOException
block|{
name|int
name|type
init|=
name|dis
operator|.
name|readUnsignedByte
argument_list|()
decl_stmt|;
switch|switch
condition|(
name|type
condition|)
block|{
case|case
literal|'B'
case|:
comment|// byte
return|return
operator|new
name|SimpleElementValueGen
argument_list|(
name|PRIMITIVE_BYTE
argument_list|,
name|dis
operator|.
name|readUnsignedShort
argument_list|()
argument_list|,
name|cpGen
argument_list|)
return|;
case|case
literal|'C'
case|:
comment|// char
return|return
operator|new
name|SimpleElementValueGen
argument_list|(
name|PRIMITIVE_CHAR
argument_list|,
name|dis
operator|.
name|readUnsignedShort
argument_list|()
argument_list|,
name|cpGen
argument_list|)
return|;
case|case
literal|'D'
case|:
comment|// double
return|return
operator|new
name|SimpleElementValueGen
argument_list|(
name|PRIMITIVE_DOUBLE
argument_list|,
name|dis
operator|.
name|readUnsignedShort
argument_list|()
argument_list|,
name|cpGen
argument_list|)
return|;
case|case
literal|'F'
case|:
comment|// float
return|return
operator|new
name|SimpleElementValueGen
argument_list|(
name|PRIMITIVE_FLOAT
argument_list|,
name|dis
operator|.
name|readUnsignedShort
argument_list|()
argument_list|,
name|cpGen
argument_list|)
return|;
case|case
literal|'I'
case|:
comment|// int
return|return
operator|new
name|SimpleElementValueGen
argument_list|(
name|PRIMITIVE_INT
argument_list|,
name|dis
operator|.
name|readUnsignedShort
argument_list|()
argument_list|,
name|cpGen
argument_list|)
return|;
case|case
literal|'J'
case|:
comment|// long
return|return
operator|new
name|SimpleElementValueGen
argument_list|(
name|PRIMITIVE_LONG
argument_list|,
name|dis
operator|.
name|readUnsignedShort
argument_list|()
argument_list|,
name|cpGen
argument_list|)
return|;
case|case
literal|'S'
case|:
comment|// short
return|return
operator|new
name|SimpleElementValueGen
argument_list|(
name|PRIMITIVE_SHORT
argument_list|,
name|dis
operator|.
name|readUnsignedShort
argument_list|()
argument_list|,
name|cpGen
argument_list|)
return|;
case|case
literal|'Z'
case|:
comment|// boolean
return|return
operator|new
name|SimpleElementValueGen
argument_list|(
name|PRIMITIVE_BOOLEAN
argument_list|,
name|dis
operator|.
name|readUnsignedShort
argument_list|()
argument_list|,
name|cpGen
argument_list|)
return|;
case|case
literal|'s'
case|:
comment|// String
return|return
operator|new
name|SimpleElementValueGen
argument_list|(
name|STRING
argument_list|,
name|dis
operator|.
name|readUnsignedShort
argument_list|()
argument_list|,
name|cpGen
argument_list|)
return|;
case|case
literal|'e'
case|:
comment|// Enum constant
return|return
operator|new
name|EnumElementValueGen
argument_list|(
name|dis
operator|.
name|readUnsignedShort
argument_list|()
argument_list|,
name|dis
operator|.
name|readUnsignedShort
argument_list|()
argument_list|,
name|cpGen
argument_list|)
return|;
case|case
literal|'c'
case|:
comment|// Class
return|return
operator|new
name|ClassElementValueGen
argument_list|(
name|dis
operator|.
name|readUnsignedShort
argument_list|()
argument_list|,
name|cpGen
argument_list|)
return|;
case|case
literal|'@'
case|:
comment|// Annotation
comment|// TODO: isRuntimeVisible ??????????
comment|// FIXME
return|return
operator|new
name|AnnotationElementValueGen
argument_list|(
name|ANNOTATION
argument_list|,
operator|new
name|AnnotationEntryGen
argument_list|(
name|AnnotationEntry
operator|.
name|read
argument_list|(
name|dis
argument_list|,
name|cpGen
operator|.
name|getConstantPool
argument_list|()
argument_list|,
literal|true
argument_list|)
argument_list|,
name|cpGen
argument_list|,
literal|false
argument_list|)
argument_list|,
name|cpGen
argument_list|)
return|;
case|case
literal|'['
case|:
comment|// Array
name|int
name|numArrayVals
init|=
name|dis
operator|.
name|readUnsignedShort
argument_list|()
decl_stmt|;
name|List
name|arrayVals
init|=
operator|new
name|ArrayList
argument_list|()
decl_stmt|;
name|ElementValue
index|[]
name|evalues
init|=
operator|new
name|ElementValue
index|[
name|numArrayVals
index|]
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
name|numArrayVals
condition|;
name|j
operator|++
control|)
block|{
name|evalues
index|[
name|j
index|]
operator|=
name|ElementValue
operator|.
name|readElementValue
argument_list|(
name|dis
argument_list|,
name|cpGen
operator|.
name|getConstantPool
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
operator|new
name|ArrayElementValueGen
argument_list|(
name|ARRAY
argument_list|,
name|evalues
argument_list|,
name|cpGen
argument_list|)
return|;
default|default:
throw|throw
operator|new
name|RuntimeException
argument_list|(
literal|"Unexpected element value kind in annotation: "
operator|+
name|type
argument_list|)
throw|;
block|}
block|}
specifier|protected
name|ConstantPoolGen
name|getConstantPool
parameter_list|()
block|{
return|return
name|cpGen
return|;
block|}
comment|/** 	 * Creates an (modifiable) ElementValueGen copy of an (immutable) 	 * ElementValue - constant pool is assumed correct. 	 */
specifier|public
specifier|static
name|ElementValueGen
name|copy
parameter_list|(
name|ElementValue
name|value
parameter_list|,
name|ConstantPoolGen
name|cpool
parameter_list|,
name|boolean
name|copyPoolEntries
parameter_list|)
block|{
switch|switch
condition|(
name|value
operator|.
name|getElementValueType
argument_list|()
condition|)
block|{
case|case
literal|'B'
case|:
comment|// byte
case|case
literal|'C'
case|:
comment|// char
case|case
literal|'D'
case|:
comment|// double
case|case
literal|'F'
case|:
comment|// float
case|case
literal|'I'
case|:
comment|// int
case|case
literal|'J'
case|:
comment|// long
case|case
literal|'S'
case|:
comment|// short
case|case
literal|'Z'
case|:
comment|// boolean
case|case
literal|'s'
case|:
comment|// String
return|return
operator|new
name|SimpleElementValueGen
argument_list|(
operator|(
name|SimpleElementValue
operator|)
name|value
argument_list|,
name|cpool
argument_list|,
name|copyPoolEntries
argument_list|)
return|;
case|case
literal|'e'
case|:
comment|// Enum constant
return|return
operator|new
name|EnumElementValueGen
argument_list|(
operator|(
name|EnumElementValue
operator|)
name|value
argument_list|,
name|cpool
argument_list|,
name|copyPoolEntries
argument_list|)
return|;
case|case
literal|'@'
case|:
comment|// Annotation
return|return
operator|new
name|AnnotationElementValueGen
argument_list|(
operator|(
name|AnnotationElementValue
operator|)
name|value
argument_list|,
name|cpool
argument_list|,
name|copyPoolEntries
argument_list|)
return|;
case|case
literal|'['
case|:
comment|// Array
return|return
operator|new
name|ArrayElementValueGen
argument_list|(
operator|(
name|ArrayElementValue
operator|)
name|value
argument_list|,
name|cpool
argument_list|,
name|copyPoolEntries
argument_list|)
return|;
case|case
literal|'c'
case|:
comment|// Class
return|return
operator|new
name|ClassElementValueGen
argument_list|(
operator|(
name|ClassElementValue
operator|)
name|value
argument_list|,
name|cpool
argument_list|,
name|copyPoolEntries
argument_list|)
return|;
default|default:
throw|throw
operator|new
name|RuntimeException
argument_list|(
literal|"Not implemented yet! ("
operator|+
name|value
operator|.
name|getElementValueType
argument_list|()
operator|+
literal|")"
argument_list|)
throw|;
block|}
block|}
block|}
end_class

end_unit

