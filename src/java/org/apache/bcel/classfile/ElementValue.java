begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Copyright  2000-2004 The Apache Software Foundation  *  *  Licensed under the Apache License, Version 2.0 (the "License");  *  you may not use this file except in compliance with the License.  *  You may obtain a copy of the License at  *  *      http://www.apache.org/licenses/LICENSE-2.0  *  *  Unless required by applicable law or agreed to in writing, software  *  distributed under the License is distributed on an "AS IS" BASIS,  *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *  See the License for the specific language governing permissions and  *  limitations under the License.  *  */
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
name|DataInputStream
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

begin_comment
comment|/**  * an ElementValuePair's element value. This class will be broken out into  * different subclasses. This is a temporary implementation.  *   * @version $Id: ElementValue  * @author<A HREF="mailto:dbrosius@qis.net">D. Brosius</A>  */
end_comment

begin_class
specifier|public
class|class
name|ElementValue
block|{
specifier|private
name|byte
name|tag
decl_stmt|;
specifier|private
name|int
name|const_value_index
decl_stmt|;
specifier|private
name|int
name|type_name_index
decl_stmt|;
specifier|private
name|int
name|const_name_index
decl_stmt|;
specifier|private
name|int
name|class_info_index
decl_stmt|;
specifier|private
name|AnnotationEntry
name|annotation
decl_stmt|;
specifier|private
name|int
name|num_values
decl_stmt|;
specifier|private
name|ElementValue
index|[]
name|values
decl_stmt|;
comment|/** 	 * Construct object from file stream. 	 * @param file Input stream 	 * @param constant_pool the constant pool 	 * @throws IOException 	 */
name|ElementValue
parameter_list|(
name|DataInputStream
name|file
parameter_list|,
name|ConstantPool
name|constant_pool
parameter_list|)
throws|throws
name|IOException
block|{
name|tag
operator|=
operator|(
name|file
operator|.
name|readByte
argument_list|()
operator|)
expr_stmt|;
switch|switch
condition|(
name|tag
condition|)
block|{
case|case
literal|'B'
case|:
case|case
literal|'C'
case|:
case|case
literal|'D'
case|:
case|case
literal|'F'
case|:
case|case
literal|'I'
case|:
case|case
literal|'J'
case|:
case|case
literal|'S'
case|:
case|case
literal|'Z'
case|:
case|case
literal|'s'
case|:
name|const_value_index
operator|=
operator|(
name|file
operator|.
name|readUnsignedShort
argument_list|()
operator|)
expr_stmt|;
break|break;
case|case
literal|'e'
case|:
name|type_name_index
operator|=
operator|(
name|file
operator|.
name|readUnsignedShort
argument_list|()
operator|)
expr_stmt|;
name|const_name_index
operator|=
operator|(
name|file
operator|.
name|readUnsignedShort
argument_list|()
operator|)
expr_stmt|;
break|break;
case|case
literal|'c'
case|:
name|class_info_index
operator|=
operator|(
name|file
operator|.
name|readUnsignedShort
argument_list|()
operator|)
expr_stmt|;
break|break;
case|case
literal|'@'
case|:
name|annotation
operator|=
operator|new
name|AnnotationEntry
argument_list|(
name|file
argument_list|,
name|constant_pool
argument_list|)
expr_stmt|;
break|break;
case|case
literal|'['
case|:
name|num_values
operator|=
operator|(
name|file
operator|.
name|readUnsignedShort
argument_list|()
operator|)
expr_stmt|;
name|values
operator|=
operator|new
name|ElementValue
index|[
name|num_values
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
name|num_values
condition|;
name|i
operator|++
control|)
name|values
index|[
name|i
index|]
operator|=
operator|new
name|ElementValue
argument_list|(
name|file
argument_list|,
name|constant_pool
argument_list|)
expr_stmt|;
break|break;
default|default:
throw|throw
operator|new
name|IOException
argument_list|(
literal|"Invalid ElementValue tag: "
operator|+
name|tag
argument_list|)
throw|;
block|}
block|}
block|}
end_class

end_unit
