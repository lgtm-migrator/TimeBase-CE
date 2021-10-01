package com.epam.deltix.qsrv.test.messages;

import com.epam.deltix.dfp.Decimal;
import com.epam.deltix.timebase.messages.MessageInterface;
import com.epam.deltix.timebase.messages.RecordInfo;
import com.epam.deltix.util.collections.generated.*;

/**
 */
public interface AllListsMessageInterface extends AllListsMessageInfo, MessageInterface {
  /**
   * @param value - Nested Boolean List
   */
  void setNestedBooleanList(ByteArrayList value);

  /**
   */
  void nullifyNestedBooleanList();

  /**
   * @param value - Nested Byte List
   */
  void setNestedByteList(ByteArrayList value);

  /**
   */
  void nullifyNestedByteList();

  /**
   * @param value - Nested Short List
   */
  void setNestedShortList(ShortArrayList value);

  /**
   */
  void nullifyNestedShortList();

  /**
   * @param value - Nested Int List
   */
  void setNestedIntList(IntegerArrayList value);

  /**
   */
  void nullifyNestedIntList();

  /**
   * @param value - Nested Long List
   */
  void setNestedLongList(LongArrayList value);

  /**
   */
  void nullifyNestedLongList();

  /**
   * @param value - Nested Decimal List
   */
  void setNestedDecimalList(@Decimal LongArrayList value);

  /**
   */
  void nullifyNestedDecimalList();

  /**
   * @param value - Nested Double List
   */
  void setNestedDoubleList(DoubleArrayList value);

  /**
   */
  void nullifyNestedDoubleList();

  /**
   * @param value - Nested Float List
   */
  void setNestedFloatList(FloatArrayList value);

  /**
   */
  void nullifyNestedFloatList();

  /**
   * @param value - Nested Text List
   */
  void setNestedTextList(ObjectArrayList<CharSequence> value);

  /**
   */
  void nullifyNestedTextList();

  /**
   * @param value - Nested Ascii Text List
   */
  void setNestedAsciiTextList(ObjectArrayList<CharSequence> value);

  /**
   */
  void nullifyNestedAsciiTextList();

  /**
   * @param value - Nested Alphanumeric List
   */
  void setNestedAlphanumericList(LongArrayList value);

  /**
   */
  void nullifyNestedAlphanumericList();

  /**
   * @param value - Nested Objects List
   */
  void setNestedObjectsList(ObjectArrayList<AllSimpleTypesMessageInfo> value);

  /**
   */
  void nullifyNestedObjectsList();

  /**
   * Method nullifies all instance properties
   */
  @Override
  AllListsMessageInterface nullify();

  /**
   * Resets all instance properties to their default values
   */
  @Override
  AllListsMessageInterface reset();

  @Override
  AllListsMessageInterface copyFrom(RecordInfo template);
}
