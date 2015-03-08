package com.stormcloud.woodrow.DictionaryEntry;

import android.util.Log;

/**
 * http://en.wiktionary.org/w/index.php?title=onus&action=raw
 * <p/>
 * Example result from query:
 * <p/>
 * ==English==
 * <p/>
 * ===Etymology===
 * From {{etyl|la|en}} {{term|onus||burden|lang=la}}.
 * <p/>
 * ===Pronunciation===
 * {{a|RP}} {{IPA|/ˈəʊnəs/|lang=en}}
 * {{a|US}} {{IPA|/ˈoʊnəs/|lang=en}}
 * {{rhymes|əʊnəs|lang=en}}
 * <p/>
 * ===Noun===
 * {{en-noun|~|onuses}}
 * <p/>
 * # A [[legal]] [[obligation]].
 * #: ''The '''onus''' is on the landlord to make sure the walls are protected from mildew.''
 * # {{context|uncountable|lang=en}} [[burden of proof|Burden of proof]], [[onus probandi]]
 * #: ''The '''onus''' is on those who disagree with my proposal to explain why.''
 * # [[stigma|Stigma]].
 * #* {{quote-book|title=Godiva's Ride: Women of Letters in England, 1830-1880|page=19|author=Dorothy Mermin|year=1993|passage=Geraldine evades the '''onus''' of ambition by subordinating it to the service of her family, and escapes the '''onus''' of sexuality by bodily mutilation}}
 * # [[blame|Blame]].
 * #* {{quote-book|title=Shattered Peace: The Origins of the Cold War and the National Security State|page=6|author=Daniel Yergin|year=1977|passage=... what might be called "'''onus'''-shifting" — each side trying to make a record and place blame on the other for the division of Europe and the Cold War itself.}}
 * # [[responsibility|Responsibility]]; [[burden]].
 * #* {{quote-book|title=The Beatles Anthology|page=174|author=Beatles|coauthors=Brian Roylance, Paul McCartney, John Lennon, George Harrison, Ringo Starr|year=2000|passage=The '''onus''' isn't on us to produce something great every time. The '''onus''' is on the public to decide whether they like it or not.}}
 * <p/>
 * ====Related terms====
 * [[exonerate]]
 * [[exoneration]]
 * [[onerous]]
 * [[onus probandi]]
 * <p/>
 * ====Translations====
 * {{trans-top|legal obligation}}
 * Dutch: {{t+|nl|last|m}}, {{t+|nl|plicht|f}}
 * German: {{t+|de|Pflicht|f}}
 * Italian: {{t+|it|onere}}
 * Serbo-Croatian: {{t+|sh|obaveza}}
 * Spanish: {{t+|es|obligación|f}}
 * {{trans-mid}}
 * Turkish: {{t+|tr|sorumluluk}}, {{t+|tr|yük}}
 * {{trans-bottom}}
 * <p/>
 * {{trans-top|burden of proof}}
 * Dutch: {{t+|nl|last|m}}, {{t|nl|bewijslast|m}}
 * {{trans-mid}}
 * German: {{t|de|Beweislast|f}}
 * Icelandic: {{t|is|sönnunarbyrði|f}}
 * Italian: {{t|it|onere della prova}}
 * Serbo-Croatian: {{t|sh|teret dokaza|m}}
 * {{trans-bottom}}
 * <p/>
 * {{trans-top|stigma}}
 * {{trans-mid}}
 * {{trans-bottom}}
 * <p/>
 * {{trans-top|blame}}
 * {{trans-mid}}
 * Serbo-Croatian: {{t+|sh|breme|n}}
 * {{trans-bottom}}
 * <p/>
 * ===Anagrams===
 * [[nous#English|nous]]
 * <p/>
 * ----
 * ==Dutch==
 * <p/>
 * ===Etymology===
 * From {{etyl|la|nl}} {{term|onus||burden|lang=la}}.
 * <p/>
 * ===Noun===
 * {{nl-noun|m|-en|pl2=oni|onusje}}
 * <p/>
 * # [[burden]]
 * <p/>
 * ----
 * <p/>
 * ==Latin==
 * ===Etymology===
 * From {{etyl|ine-pro|la}} {{m|ine-pro|*h₃énh₂os}} from {{m|ine-pro|*h₃enh₂-}}. See {{etyl|grc|-}} {{m|grc|ὄνομαι||impugn, quarrel with}}.
 * <p/>
 * ===Pronunciation===
 * {{la-pronunc|onus}}
 * <p/>
 * ===Noun===
 * {{la-noun|onus|oneris|oneris|n|third}}
 * <p/>
 * # [[burden]], [[load]]
 * <p/>
 * ====Inflection====
 * {{la-decl-3rd-N|onus|oner}}
 * <p/>
 * ====Derived terms====
 * {{l|la|onerō}}
 * {{l|la|onustus}}
 * {{l|la|onus probandī}}
 * <p/>
 * ====Descendants====
 * Dutch: {{l|nl|onus}}
 * English: {{l|en|onus}}
 * Portuguese: {{l|pt|ónus}}
 * <p/>
 * ===References===
 * CLARKSON, James, ''Indo-European Word Formation: Proceedings from the International Conference'', 2002
 * <p/>
 * [[cs:onus]]
 * [[et:onus]]
 * [[el:onus]]
 * [[fr:onus]]
 * [[ko:onus]]
 * [[hy:onus]]
 * [[io:onus]]
 * [[it:onus]]
 * [[kn:onus]]
 * [[ku:onus]]
 * [[hu:onus]]
 * [[mg:onus]]
 * [[ml:onus]]
 * [[pl:onus]]
 * [[pt:onus]]
 * [[fi:onus]]
 * [[sv:onus]]
 * [[ta:onus]]
 * [[te:onus]]
 * [[vi:onus]]
 * [[zh:onus]]*
 * Created by schhan on 3/7/15.
 */

public class DictionaryEntryWiktionaryParser {

    //    private static final String URL_ENTRY_POINT = "http://en.wiktionary.org/w/index.php";
    public static final String HEADER_PRONUNCIATION = "===Pronunciation===";

    // May add other parts of speech in further revisions
    public static final String PartsOfSpeech[] = {
            "===Noun===", "===Verb===", "===Adjective===", "===Adverb===", "===Pronoun==="

    };


    public DictionaryEntry parse(String text) {
        /**
         * First, we get the pronunciation*
         *
         * Then get the definition
         *
         * Then store the definition in a String:
         *      Replace any symbols with blank spaces
         *
         *Create a new WordEntry with the parsed information
         *      WordEntry word = new Word(word, definition, etc.)*
         */


        // Get the language
        String language = getLanguage(text);

        // Get the pronunciation (can be a list if multiple pronunciations?)
        String pronunciation = getPronunciation(text);

        // Get the definition
        String definition = getBestDefinition(text);

        return new DictionaryEntry(null, null, pronunciation, definition);
    }

    /**
     * *
     *
     * @param text to be parsed
     * @return String value of text language
     */
    public String getLanguage(String text) {

        // Begin Language section
        String language;
        String twoqual = "==";
        int current_twoqual_occurrence;


        if (text.contains(twoqual)) {

            current_twoqual_occurrence = text.indexOf(twoqual);

            language = text.substring(current_twoqual_occurrence + twoqual.length(), text.indexOf(twoqual, current_twoqual_occurrence + twoqual.length()));

            return language;

        }

        return null;

    }

    /**
     * This method can be improved by returning a list of all the valid pronunciations given to us by Wiktionary.
     * <p/>
     * There are cases where a Wiktionary entry does not offer a pronunciation, in this case cautious of nulls
     *
     * @param text to be parsed
     * @return String value of pronunciation
     */
    public String getPronunciation(String text) {

        String pronunciation;
        int current_pronunciation_header_occurrence, current_IPA_occurrence;


        if (text.contains(HEADER_PRONUNCIATION)) {
            current_pronunciation_header_occurrence = text.indexOf(HEADER_PRONUNCIATION);
            current_IPA_occurrence = text.indexOf("IPA", current_pronunciation_header_occurrence + HEADER_PRONUNCIATION.length());

            // Locate the first forward slash
            int opening_forward_slash_occurrence = text.indexOf("/", current_IPA_occurrence);
            int closing_forward_slash_occurrence = text.indexOf("/", opening_forward_slash_occurrence + 1);

            pronunciation = text.substring(opening_forward_slash_occurrence, closing_forward_slash_occurrence + 1);

            return pronunciation;
        }

        return null;

    }

    /**
     * For enties containing multiple parts of speech, gauge which is "BEST" by counting
     * number of hashtags each part has. Return definition with greatest number of hashtags.
     *
     * @param text
     * @return
     */
    public String getBestDefinition(String text) {

        // Begin the part of speech
        String speech_part;
        String definition;

        int current_sp_occurrence, current_hashtag_occurrence = 0, next_n_equal_occurrence;

        int fourdash_occurrence;

        for (String sp : PartsOfSpeech) {
            if (text.contains(sp)) {
                current_sp_occurrence = text.indexOf(sp) + sp.length();
                fourdash_occurrence = text.indexOf("----");

                // If we see the four dashes occur before the part of speech header, we've probably
                // ventured into another language - S.C. 08/03/2015
                if (fourdash_occurrence > -1 && fourdash_occurrence < current_sp_occurrence) {
                    continue;
                }

                current_hashtag_occurrence = text.indexOf("#", current_sp_occurrence);

                break;
            }
        }

        definition = text.substring(current_hashtag_occurrence + 1, text.indexOf("\n", current_hashtag_occurrence + 1));

        // Clean up the text
        if (definition.contains("|")) {
            definition = definition.substring(definition.lastIndexOf("|") + 1);
        }

        if (definition.contains("}")) {
            definition = definition.substring(definition.lastIndexOf("}") + 1);
        }

        definition = definition.replace("[", "");
        definition = definition.replace("]", "");

        definition = definition.trim();

        return definition;

    }

}
