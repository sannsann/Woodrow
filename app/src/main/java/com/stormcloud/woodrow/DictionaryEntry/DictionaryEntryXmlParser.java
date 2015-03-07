package com.stormcloud.woodrow.DictionaryEntry;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * http://www.dictionaryapi.com/api/v1/references/collegiate/xml/hypocrite?key=[YOUR KEY GOES HERE]
 * <p/>
 * Use the schema above to retrieve the entry in XML format.
 * <p/>
 * <p/>
 * // Analyze the feed:
 * //  We want an entry: e.g. <entry id="hypocrite">, inside are  child elements that must be
 * // extracted:
 * //          <hw>(syllable breakdown),
 * //          <pr> (pronunciation),
 * //          <dt> definition... anything else?* **
 * Created by schhan on 3/2/15.
 */
public class DictionaryEntryXmlParser {

    private static final String URL_STEM = "http://www.dictionaryapi.com/api/v1/references/collegiate/xml/";
    private static final String API_KEY = "a151e819-6103-452b-a42a-438e13922ae6";
    
    // We don't use namespaces
    private final String ns = null;

    private final String ENTRY_LIST = "entry_list";
    private final String ENTRY_ENTRY = "entry";
    private final String ENTRY_WORD = "ew";
    private final String ENTRY_SYLLABLES = "hw";
    private final String ENTRY_DEFINITION = "def";
    private final String ENTRY_DEFINITION_TEXT = "dt";
    private final String ENTRY_PRONUNCIATION = "pr";

    public List parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readFeed(parser);
        } finally {
            in.close();
        }
    }

    // Reads the feed. This method does the heavy work.
    private List readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        List entries = new ArrayList();

        parser.require(XmlPullParser.START_TAG, ns, ENTRY_LIST);
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals(ENTRY_ENTRY)) {
                entries.add(readEntry(parser));
            } else {
                skip(parser);
            }
        }

        return entries;
    }

    // Parse the XML
    private DictionaryEntry readEntry(XmlPullParser parser) throws XmlPullParserException,
            IOException {

        parser.require(XmlPullParser.START_TAG, ns, ENTRY_ENTRY);
        String word = null;
        String syllables = null;
        String pronunciation = null;
        String definition = null;

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals(ENTRY_WORD)) {
                word = readWord(parser);
            } else if (name.equals(ENTRY_SYLLABLES)) {
                syllables = readSyllables(parser);
            } else if (name.equals(ENTRY_PRONUNCIATION)) {
                pronunciation = readPronunciation(parser);
            } else if (name.equals(ENTRY_DEFINITION)) {
                definition = readDefinition(parser);
            }
            else {
                skip(parser);
            }
        }

        return new DictionaryEntry(word, syllables, pronunciation, definition);

    }

    // Processes word tags in the feed
    private String readWord(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, ENTRY_WORD);
        String word = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, ENTRY_WORD);
        return word;
    }

    private String readSyllables(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, ENTRY_SYLLABLES);
        String syllables = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, ENTRY_SYLLABLES);
        return syllables;
    }

    private String readPronunciation(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, ENTRY_PRONUNCIATION);
        String pronunciation = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, ENTRY_PRONUNCIATION);
        return pronunciation;
    }

    private String readDefinition(XmlPullParser parser) throws IOException, XmlPullParserException {

        String definition = "";
//        parser.require(XmlPullParser.START_TAG, ns, ENTRY_DEFINITION);

        while (parser.next() != XmlPullParser.END_TAG) {

            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            String name = parser.getName();
            if (name.equals(ENTRY_DEFINITION_TEXT)) {
//                parser.require(XmlPullParser.START_TAG, ns, ENTRY_DEFINITION_TEXT);
                definition += ": " + readText(parser);
//                parser.require(XmlPullParser.END_TAG, ns, ENTRY_DEFINITION_TEXT);
//                break;
            } else {
                skip(parser);
            }
        }

        return definition;
    }

    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }

        return result;
    }

    // Skip tags we don't care about
    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }

        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
}
