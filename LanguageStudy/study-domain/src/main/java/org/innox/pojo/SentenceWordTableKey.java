package org.innox.pojo;

public class SentenceWordTableKey {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sentence_word_table.word_id
     *
     * @mbg.generated Mon May 24 13:50:34 JST 2021
     */
    private Long wordId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sentence_word_table.sentence_seq
     *
     * @mbg.generated Mon May 24 13:50:34 JST 2021
     */
    private Long sentenceSeq;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sentence_word_table.word_id
     *
     * @return the value of sentence_word_table.word_id
     *
     * @mbg.generated Mon May 24 13:50:34 JST 2021
     */
    public Long getWordId() {
        return wordId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sentence_word_table.word_id
     *
     * @param wordId the value for sentence_word_table.word_id
     *
     * @mbg.generated Mon May 24 13:50:34 JST 2021
     */
    public void setWordId(Long wordId) {
        this.wordId = wordId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sentence_word_table.sentence_seq
     *
     * @return the value of sentence_word_table.sentence_seq
     *
     * @mbg.generated Mon May 24 13:50:34 JST 2021
     */
    public Long getSentenceSeq() {
        return sentenceSeq;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sentence_word_table.sentence_seq
     *
     * @param sentenceSeq the value for sentence_word_table.sentence_seq
     *
     * @mbg.generated Mon May 24 13:50:34 JST 2021
     */
    public void setSentenceSeq(Long sentenceSeq) {
        this.sentenceSeq = sentenceSeq;
    }
}