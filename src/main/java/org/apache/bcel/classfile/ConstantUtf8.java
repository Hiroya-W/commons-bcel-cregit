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
name|java
operator|.
name|util
operator|.
name|HashMap
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|LinkedHashMap
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Objects
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

begin_comment
comment|/**  * Extends the abstract {@link Constant} to represent a reference to a UTF-8 encoded string.  *<p>  * The following system properties govern caching this class performs.  *</p>  *<ul>  *<li>{@value #SYS_PROP_CACHE_MAX_ENTRIES} (since 6.4): The size of the cache, by default 0, meaning caching is  * disabled.</li>  *<li>{@value #SYS_PROP_CACHE_MAX_ENTRY_SIZE} (since 6.0): The maximum size of the values to cache, by default 200, 0  * disables caching. Values larger than this are<em>not</em> cached.</li>  *<li>{@value #SYS_PROP_STATISTICS} (since 6.0): Prints statistics on the console when the JVM exits.</li>  *</ul>  *<p>  * Here is a sample Maven invocation with caching disabled:  *</p>  *  *<pre>  * mvn test -Dbcel.statistics=true -Dbcel.maxcached.size=0 -Dbcel.maxcached=0  *</pre>  *<p>  * Here is a sample Maven invocation with caching enabled:  *</p>  *  *<pre>  * mvn test -Dbcel.statistics=true -Dbcel.maxcached.size=100000 -Dbcel.maxcached=5000000  *</pre>  *  * @see Constant  */
end_comment

begin_class
specifier|public
specifier|final
class|class
name|ConstantUtf8
extends|extends
name|Constant
block|{
specifier|private
specifier|static
class|class
name|Cache
block|{
specifier|private
specifier|static
specifier|final
name|boolean
name|BCEL_STATISTICS
init|=
name|Boolean
operator|.
name|getBoolean
argument_list|(
name|SYS_PROP_STATISTICS
argument_list|)
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|int
name|MAX_ENTRIES
init|=
name|Integer
operator|.
name|getInteger
argument_list|(
name|SYS_PROP_CACHE_MAX_ENTRIES
argument_list|,
literal|0
argument_list|)
operator|.
name|intValue
argument_list|()
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|int
name|INITIAL_CAPACITY
init|=
operator|(
name|int
operator|)
operator|(
name|MAX_ENTRIES
operator|/
literal|0.75
operator|)
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|HashMap
argument_list|<
name|String
argument_list|,
name|ConstantUtf8
argument_list|>
name|CACHE
init|=
operator|new
name|LinkedHashMap
argument_list|<
name|String
argument_list|,
name|ConstantUtf8
argument_list|>
argument_list|(
name|INITIAL_CAPACITY
argument_list|,
literal|0.75f
argument_list|,
literal|true
argument_list|)
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
operator|-
literal|8506975356158971766L
decl_stmt|;
annotation|@
name|Override
specifier|protected
name|boolean
name|removeEldestEntry
parameter_list|(
specifier|final
name|Map
operator|.
name|Entry
argument_list|<
name|String
argument_list|,
name|ConstantUtf8
argument_list|>
name|eldest
parameter_list|)
block|{
return|return
name|size
argument_list|()
operator|>
name|MAX_ENTRIES
return|;
block|}
block|}
decl_stmt|;
comment|// Set the size to 0 or below to skip caching entirely
specifier|private
specifier|static
specifier|final
name|int
name|MAX_ENTRY_SIZE
init|=
name|Integer
operator|.
name|getInteger
argument_list|(
name|SYS_PROP_CACHE_MAX_ENTRY_SIZE
argument_list|,
literal|200
argument_list|)
operator|.
name|intValue
argument_list|()
decl_stmt|;
specifier|static
name|boolean
name|isEnabled
parameter_list|()
block|{
return|return
name|Cache
operator|.
name|MAX_ENTRIES
operator|>
literal|0
operator|&&
name|MAX_ENTRY_SIZE
operator|>
literal|0
return|;
block|}
block|}
comment|// TODO these should perhaps be AtomicInt?
specifier|private
specifier|static
specifier|volatile
name|int
name|considered
decl_stmt|;
specifier|private
specifier|static
specifier|volatile
name|int
name|created
decl_stmt|;
specifier|private
specifier|static
specifier|volatile
name|int
name|hits
decl_stmt|;
specifier|private
specifier|static
specifier|volatile
name|int
name|skipped
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|SYS_PROP_CACHE_MAX_ENTRIES
init|=
literal|"bcel.maxcached"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|SYS_PROP_CACHE_MAX_ENTRY_SIZE
init|=
literal|"bcel.maxcached.size"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|SYS_PROP_STATISTICS
init|=
literal|"bcel.statistics"
decl_stmt|;
static|static
block|{
if|if
condition|(
name|Cache
operator|.
name|BCEL_STATISTICS
condition|)
block|{
name|Runtime
operator|.
name|getRuntime
argument_list|()
operator|.
name|addShutdownHook
argument_list|(
operator|new
name|Thread
argument_list|(
name|ConstantUtf8
operator|::
name|printStats
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Clears the cache.      *      * @since 6.4.0      */
specifier|public
specifier|static
specifier|synchronized
name|void
name|clearCache
parameter_list|()
block|{
name|Cache
operator|.
name|CACHE
operator|.
name|clear
argument_list|()
expr_stmt|;
block|}
comment|// for access by test code
specifier|static
specifier|synchronized
name|void
name|clearStats
parameter_list|()
block|{
name|hits
operator|=
name|considered
operator|=
name|skipped
operator|=
name|created
operator|=
literal|0
expr_stmt|;
block|}
comment|/**      * Gets a new or cached instance of the given value.      *<p>      * See {@link ConstantUtf8} class Javadoc for details.      *</p>      *      * @param value the value.      * @return a new or cached instance of the given value.      * @since 6.0      */
specifier|public
specifier|static
name|ConstantUtf8
name|getCachedInstance
parameter_list|(
specifier|final
name|String
name|value
parameter_list|)
block|{
if|if
condition|(
name|value
operator|.
name|length
argument_list|()
operator|>
name|Cache
operator|.
name|MAX_ENTRY_SIZE
condition|)
block|{
name|skipped
operator|++
expr_stmt|;
return|return
operator|new
name|ConstantUtf8
argument_list|(
name|value
argument_list|)
return|;
block|}
name|considered
operator|++
expr_stmt|;
synchronized|synchronized
init|(
name|ConstantUtf8
operator|.
name|class
init|)
block|{
comment|// might be better with a specific lock object
name|ConstantUtf8
name|result
init|=
name|Cache
operator|.
name|CACHE
operator|.
name|get
argument_list|(
name|value
argument_list|)
decl_stmt|;
if|if
condition|(
name|result
operator|!=
literal|null
condition|)
block|{
name|hits
operator|++
expr_stmt|;
return|return
name|result
return|;
block|}
name|result
operator|=
operator|new
name|ConstantUtf8
argument_list|(
name|value
argument_list|)
expr_stmt|;
name|Cache
operator|.
name|CACHE
operator|.
name|put
argument_list|(
name|value
argument_list|,
name|result
argument_list|)
expr_stmt|;
return|return
name|result
return|;
block|}
block|}
comment|/**      * Gets a new or cached instance of the given value.      *<p>      * See {@link ConstantUtf8} class Javadoc for details.      *</p>      *      * @param dataInput the value.      * @return a new or cached instance of the given value.      * @throws IOException if an I/O error occurs.      * @since 6.0      */
specifier|public
specifier|static
name|ConstantUtf8
name|getInstance
parameter_list|(
specifier|final
name|DataInput
name|dataInput
parameter_list|)
throws|throws
name|IOException
block|{
return|return
name|getInstance
argument_list|(
name|dataInput
operator|.
name|readUTF
argument_list|()
argument_list|)
return|;
block|}
comment|/**      * Gets a new or cached instance of the given value.      *<p>      * See {@link ConstantUtf8} class Javadoc for details.      *</p>      *      * @param value the value.      * @return a new or cached instance of the given value.      * @since 6.0      */
specifier|public
specifier|static
name|ConstantUtf8
name|getInstance
parameter_list|(
specifier|final
name|String
name|value
parameter_list|)
block|{
return|return
name|Cache
operator|.
name|isEnabled
argument_list|()
condition|?
name|getCachedInstance
argument_list|(
name|value
argument_list|)
else|:
operator|new
name|ConstantUtf8
argument_list|(
name|value
argument_list|)
return|;
block|}
comment|// for access by test code
specifier|static
name|void
name|printStats
parameter_list|()
block|{
specifier|final
name|String
name|prefix
init|=
literal|"[Apache Commons BCEL]"
decl_stmt|;
name|System
operator|.
name|err
operator|.
name|printf
argument_list|(
literal|"%s Cache hit %,d/%,d, %d skipped.%n"
argument_list|,
name|prefix
argument_list|,
name|hits
argument_list|,
name|considered
argument_list|,
name|skipped
argument_list|)
expr_stmt|;
name|System
operator|.
name|err
operator|.
name|printf
argument_list|(
literal|"%s Total of %,d ConstantUtf8 objects created.%n"
argument_list|,
name|prefix
argument_list|,
name|created
argument_list|)
expr_stmt|;
name|System
operator|.
name|err
operator|.
name|printf
argument_list|(
literal|"%s Configuration: %s=%,d, %s=%,d.%n"
argument_list|,
name|prefix
argument_list|,
name|SYS_PROP_CACHE_MAX_ENTRIES
argument_list|,
name|Cache
operator|.
name|MAX_ENTRIES
argument_list|,
name|SYS_PROP_CACHE_MAX_ENTRY_SIZE
argument_list|,
name|Cache
operator|.
name|MAX_ENTRY_SIZE
argument_list|)
expr_stmt|;
block|}
specifier|private
specifier|final
name|String
name|value
decl_stmt|;
comment|/**      * Initializes from another object.      *      * @param constantUtf8 the value.      */
specifier|public
name|ConstantUtf8
parameter_list|(
specifier|final
name|ConstantUtf8
name|constantUtf8
parameter_list|)
block|{
name|this
argument_list|(
name|constantUtf8
operator|.
name|getBytes
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**      * Initializes instance from file data.      *      * @param dataInput Input stream      * @throws IOException if an I/O error occurs.      */
name|ConstantUtf8
parameter_list|(
specifier|final
name|DataInput
name|dataInput
parameter_list|)
throws|throws
name|IOException
block|{
name|super
argument_list|(
name|Const
operator|.
name|CONSTANT_Utf8
argument_list|)
expr_stmt|;
name|value
operator|=
name|dataInput
operator|.
name|readUTF
argument_list|()
expr_stmt|;
name|created
operator|++
expr_stmt|;
block|}
comment|/**      * @param value Data      */
specifier|public
name|ConstantUtf8
parameter_list|(
specifier|final
name|String
name|value
parameter_list|)
block|{
name|super
argument_list|(
name|Const
operator|.
name|CONSTANT_Utf8
argument_list|)
expr_stmt|;
name|this
operator|.
name|value
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|value
argument_list|,
literal|"value"
argument_list|)
expr_stmt|;
name|created
operator|++
expr_stmt|;
block|}
comment|/**      * Called by objects that are traversing the nodes of the tree implicitly defined by the contents of a Java class.      * I.e., the hierarchy of methods, fields, attributes, etc. spawns a tree of objects.      *      * @param v Visitor object      */
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
name|visitConstantUtf8
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
comment|/**      * Dumps String in Utf8 format to file stream.      *      * @param file Output file stream      * @throws IOException if an I/O error occurs.      */
annotation|@
name|Override
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
name|writeByte
argument_list|(
name|super
operator|.
name|getTag
argument_list|()
argument_list|)
expr_stmt|;
name|file
operator|.
name|writeUTF
argument_list|(
name|value
argument_list|)
expr_stmt|;
block|}
comment|/**      * @return Data converted to string.      */
specifier|public
name|String
name|getBytes
parameter_list|()
block|{
return|return
name|value
return|;
block|}
comment|/**      * @param bytes the raw bytes of this UTF-8      * @deprecated (since 6.0)      */
annotation|@
name|java
operator|.
name|lang
operator|.
name|Deprecated
specifier|public
name|void
name|setBytes
parameter_list|(
specifier|final
name|String
name|bytes
parameter_list|)
block|{
throw|throw
operator|new
name|UnsupportedOperationException
argument_list|()
throw|;
block|}
comment|/**      * @return String representation      */
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|super
operator|.
name|toString
argument_list|()
operator|+
literal|"(\""
operator|+
name|Utility
operator|.
name|replace
argument_list|(
name|value
argument_list|,
literal|"\n"
argument_list|,
literal|"\\n"
argument_list|)
operator|+
literal|"\")"
return|;
block|}
block|}
end_class

end_unit

