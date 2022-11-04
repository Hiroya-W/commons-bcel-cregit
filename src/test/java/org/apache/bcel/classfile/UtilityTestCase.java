begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreements.  See the NOTICE file distributed with  * this work for additional information regarding copyright ownership.  * The ASF licenses this file to You under the Apache License, Version 2.0  * (the "License"); you may not use this file except in compliance with  * the License.  You may obtain a copy of the License at  *  *   http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS,  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  * See the License for the specific language governing permissions and  * limitations under the License.  */
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
import|import static
name|org
operator|.
name|junit
operator|.
name|jupiter
operator|.
name|api
operator|.
name|Assertions
operator|.
name|assertEquals
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|jupiter
operator|.
name|api
operator|.
name|Assertions
operator|.
name|assertFalse
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|jupiter
operator|.
name|api
operator|.
name|Assertions
operator|.
name|assertTrue
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
name|junit
operator|.
name|jupiter
operator|.
name|api
operator|.
name|Test
import|;
end_import

begin_class
specifier|public
class|class
name|UtilityTestCase
block|{
annotation|@
name|Test
specifier|public
name|void
name|testSignatureToStringWithGenerics
parameter_list|()
throws|throws
name|Exception
block|{
comment|// tests for BCEL-197
name|assertEquals
argument_list|(
literal|"java.util.Map<X, java.util.List<Y>>"
argument_list|,
name|Utility
operator|.
name|signatureToString
argument_list|(
literal|"Ljava/util/Map<TX;Ljava/util/List<TY;>;>;"
argument_list|)
argument_list|,
literal|"generic signature"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"java.util.Set<? extends java.nio.file.OpenOption>"
argument_list|,
name|Utility
operator|.
name|signatureToString
argument_list|(
literal|"Ljava/util/Set<+Ljava/nio/file/OpenOption;>;"
argument_list|)
argument_list|,
literal|"generic signature"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"java.nio.file.attribute.FileAttribute<?>[]"
argument_list|,
name|Utility
operator|.
name|signatureToString
argument_list|(
literal|"[Ljava/nio/file/attribute/FileAttribute<*>;"
argument_list|)
argument_list|,
literal|"generic signature"
argument_list|)
expr_stmt|;
comment|// tests for BCEL-286
name|assertEquals
argument_list|(
literal|"boofcv.alg.tracker.tld.TldTracker<boofcv.struct.image.ImageGray<boofcv.struct.image.GrayU8>, boofcv.struct.image.GrayI<boofcv.struct.image.GrayU8>>"
argument_list|,
name|Utility
operator|.
name|signatureToString
argument_list|(
literal|"Lboofcv/alg/tracker/tld/TldTracker<Lboofcv/struct/image/ImageGray<Lboofcv/struct/image/GrayU8;>;Lboofcv/struct/image/GrayI<Lboofcv/struct/image/GrayU8;>;>;"
argument_list|)
argument_list|,
literal|"generic signature"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"java.util.Map<?, ?>"
argument_list|,
name|Utility
operator|.
name|signatureToString
argument_list|(
literal|"Ljava/util/Map<**>;"
argument_list|)
argument_list|,
literal|"generic signature"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"com.jme3.util.IntMap<T>.IntMapIterator"
argument_list|,
name|Utility
operator|.
name|signatureToString
argument_list|(
literal|"Lcom/jme3/util/IntMap<TT;>.IntMapIterator;"
argument_list|)
argument_list|,
literal|"generic signature"
argument_list|)
expr_stmt|;
comment|// tests for BCEL-279
name|assertEquals
argument_list|(
literal|"<T extends java.lang.Object>(com.google.common.io.ByteProcessor<T>, int)T"
argument_list|,
name|Utility
operator|.
name|signatureToString
argument_list|(
literal|"<T:Ljava/lang/Object;>(Lcom/google/common/io/ByteProcessor<TT;>;I)TT;"
argument_list|,
literal|false
argument_list|)
argument_list|,
literal|"type parameters signature"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"<T extends Object>(com.google.common.io.ByteProcessor<T>, int)T"
argument_list|,
name|Utility
operator|.
name|signatureToString
argument_list|(
literal|"<T:Ljava/lang/Object;>(Lcom/google/common/io/ByteProcessor<TT;>;I)TT;"
argument_list|,
literal|true
argument_list|)
argument_list|,
literal|"type parameters signature"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"<M extends java.lang.reflect.AccessibleObject& java.lang.reflect.Member>(M)void"
argument_list|,
name|Utility
operator|.
name|signatureToString
argument_list|(
literal|"<M:Ljava/lang/reflect/AccessibleObject;:Ljava/lang/reflect/Member;>(TM;)V"
argument_list|)
argument_list|,
literal|"type parameters signature"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"<K1 extends K, V1 extends V>()com.google.common.cache.Weigher<K1, V1>"
argument_list|,
name|Utility
operator|.
name|signatureToString
argument_list|(
literal|"<K1:TK;V1:TV;>()Lcom/google/common/cache/Weigher<TK1;TV1;>;"
argument_list|)
argument_list|,
literal|"type parameters signature"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"<K1 extends K, V1 extends V>(com.google.common.cache.Weigher<? super K1, ? super V1>)com.google.common.cache.CacheBuilder<K1, V1>"
argument_list|,
name|Utility
operator|.
name|signatureToString
argument_list|(
literal|"<K1:TK;V1:TV;>(Lcom/google/common/cache/Weigher<-TK1;-TV1;>;)Lcom/google/common/cache/CacheBuilder<TK1;TV1;>;"
argument_list|)
argument_list|,
literal|"type parameters signature"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"<N extends java.lang.Object, E extends java.lang.Object> extends java.lang.Object implements com.google.common.graph.Network<N, E>"
argument_list|,
name|Utility
operator|.
name|signatureToString
argument_list|(
literal|"<N:Ljava/lang/Object;E:Ljava/lang/Object;>Ljava/lang/Object;Lcom/google/common/graph/Network<TN;TE;>;"
argument_list|,
literal|false
argument_list|)
argument_list|,
literal|"class signature"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"<K extends Object, V extends Object> extends Object"
argument_list|,
name|Utility
operator|.
name|signatureToString
argument_list|(
literal|"<K:Ljava/lang/Object;V:Ljava/lang/Object;>Ljava/lang/Object;"
argument_list|)
argument_list|,
literal|"class signature"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testClearBit
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|0
argument_list|,
name|Utility
operator|.
name|clearBit
argument_list|(
literal|0
argument_list|,
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|0
argument_list|,
name|Utility
operator|.
name|clearBit
argument_list|(
literal|1
argument_list|,
literal|0
argument_list|)
argument_list|,
literal|"1 bit 0 set to 0 -> 0"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|Utility
operator|.
name|clearBit
argument_list|(
literal|1
argument_list|,
literal|1
argument_list|)
argument_list|,
literal|"1 bit 1 is 0 hence no change"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|8
argument_list|,
name|Utility
operator|.
name|clearBit
argument_list|(
literal|8
argument_list|,
literal|4
argument_list|)
argument_list|,
literal|"1000 only has 4 bit hence no change"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|Utility
operator|.
name|clearBit
argument_list|(
literal|9
argument_list|,
literal|3
argument_list|)
argument_list|,
literal|"1001 bit 3 set to 0 -> 0001"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
operator|-
literal|2
argument_list|,
name|Utility
operator|.
name|clearBit
argument_list|(
operator|-
literal|1
argument_list|,
literal|0
argument_list|)
argument_list|,
literal|"111...11 set bit 0 to 0 -> 111..10"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|0
argument_list|,
name|Utility
operator|.
name|clearBit
argument_list|(
name|Integer
operator|.
name|MIN_VALUE
argument_list|,
literal|31
argument_list|)
argument_list|,
literal|"100...00 set bit 31 to 0 -> 000..00"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testSetBit
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|Utility
operator|.
name|setBit
argument_list|(
literal|0
argument_list|,
literal|0
argument_list|)
argument_list|,
literal|"0 bit 0 set to 1 -> 1"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|Utility
operator|.
name|setBit
argument_list|(
literal|1
argument_list|,
literal|0
argument_list|)
argument_list|,
literal|"1 bit 0 is 1 hence no change"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|3
argument_list|,
name|Utility
operator|.
name|setBit
argument_list|(
literal|1
argument_list|,
literal|1
argument_list|)
argument_list|,
literal|"1 bit 1 set to 1 -> 3"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|8
argument_list|,
name|Utility
operator|.
name|setBit
argument_list|(
literal|8
argument_list|,
literal|3
argument_list|)
argument_list|,
literal|"1000 bit 3 is 1 hence no change"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|9
argument_list|,
name|Utility
operator|.
name|setBit
argument_list|(
literal|1
argument_list|,
literal|3
argument_list|)
argument_list|,
literal|"0001 bit 3 set to 1 -> 1001"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
operator|-
literal|1
argument_list|,
name|Utility
operator|.
name|setBit
argument_list|(
operator|-
literal|2
argument_list|,
literal|0
argument_list|)
argument_list|,
literal|"111...10 set bit 0 to 1 -> 111..11"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Integer
operator|.
name|MIN_VALUE
argument_list|,
name|Utility
operator|.
name|setBit
argument_list|(
literal|0
argument_list|,
literal|31
argument_list|)
argument_list|,
literal|"000...00 set bit 31 to 0 -> 100..00"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testIsSet
parameter_list|()
block|{
name|assertTrue
argument_list|(
name|Utility
operator|.
name|isSet
argument_list|(
literal|1
argument_list|,
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|Utility
operator|.
name|isSet
argument_list|(
literal|7
argument_list|,
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|Utility
operator|.
name|isSet
argument_list|(
literal|8
argument_list|,
literal|3
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|Utility
operator|.
name|isSet
argument_list|(
literal|9
argument_list|,
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|Utility
operator|.
name|isSet
argument_list|(
name|Integer
operator|.
name|MIN_VALUE
argument_list|,
literal|31
argument_list|)
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|Utility
operator|.
name|isSet
argument_list|(
literal|0
argument_list|,
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|Utility
operator|.
name|isSet
argument_list|(
literal|8
argument_list|,
literal|4
argument_list|)
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|Utility
operator|.
name|isSet
argument_list|(
literal|9
argument_list|,
literal|1
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testConvertString
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"\\n"
argument_list|,
name|Utility
operator|.
name|convertString
argument_list|(
literal|"\n"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"\\r"
argument_list|,
name|Utility
operator|.
name|convertString
argument_list|(
literal|"\r"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"\\\""
argument_list|,
name|Utility
operator|.
name|convertString
argument_list|(
literal|"\""
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"\\'"
argument_list|,
name|Utility
operator|.
name|convertString
argument_list|(
literal|"'"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"\\\\"
argument_list|,
name|Utility
operator|.
name|convertString
argument_list|(
literal|"\\"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"abc"
argument_list|,
name|Utility
operator|.
name|convertString
argument_list|(
literal|"abc"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testPrintArray
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|null
argument_list|,
name|Utility
operator|.
name|printArray
argument_list|(
literal|null
argument_list|,
literal|false
argument_list|,
literal|false
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|""
argument_list|,
name|Utility
operator|.
name|printArray
argument_list|(
operator|new
name|Object
index|[
literal|0
index|]
argument_list|,
literal|false
argument_list|,
literal|false
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"{}"
argument_list|,
name|Utility
operator|.
name|printArray
argument_list|(
operator|new
name|Object
index|[
literal|0
index|]
argument_list|,
literal|true
argument_list|,
literal|false
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"null"
argument_list|,
name|Utility
operator|.
name|printArray
argument_list|(
operator|new
name|Object
index|[]
block|{
literal|null
block|}
argument_list|,
literal|false
argument_list|,
literal|false
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"a, b"
argument_list|,
name|Utility
operator|.
name|printArray
argument_list|(
operator|new
name|String
index|[]
block|{
literal|"a"
block|,
literal|"b"
block|}
argument_list|,
literal|false
argument_list|,
literal|false
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"{a, b}"
argument_list|,
name|Utility
operator|.
name|printArray
argument_list|(
operator|new
name|String
index|[]
block|{
literal|"a"
block|,
literal|"b"
block|}
argument_list|,
literal|true
argument_list|,
literal|false
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"\"a\", \"b\""
argument_list|,
name|Utility
operator|.
name|printArray
argument_list|(
operator|new
name|String
index|[]
block|{
literal|"a"
block|,
literal|"b"
block|}
argument_list|,
literal|false
argument_list|,
literal|true
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"{\"a\", \"b\"}"
argument_list|,
name|Utility
operator|.
name|printArray
argument_list|(
operator|new
name|String
index|[]
block|{
literal|"a"
block|,
literal|"b"
block|}
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testSearchOpcode
parameter_list|()
block|{
name|assertEquals
argument_list|(
name|Const
operator|.
name|ALOAD
argument_list|,
name|Utility
operator|.
name|searchOpcode
argument_list|(
literal|"aload"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Const
operator|.
name|NOP
argument_list|,
name|Utility
operator|.
name|searchOpcode
argument_list|(
literal|"nop"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Const
operator|.
name|BREAKPOINT
argument_list|,
name|Utility
operator|.
name|searchOpcode
argument_list|(
literal|"breakpoint"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Const
operator|.
name|IMPDEP2
argument_list|,
name|Utility
operator|.
name|searchOpcode
argument_list|(
literal|"impdep2"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Const
operator|.
name|I2D
argument_list|,
name|Utility
operator|.
name|searchOpcode
argument_list|(
literal|"I2D"
argument_list|)
argument_list|,
literal|"case insensitive"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Const
operator|.
name|UNDEFINED
argument_list|,
name|Utility
operator|.
name|searchOpcode
argument_list|(
literal|"???"
argument_list|)
argument_list|,
literal|"not found"
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

