/**
 * Trie.java  (Prefix Tree / Digital Tree)
 *
 * A Trie stores strings character-by-character.
 * Each node represents one character; paths from root to a marked node = a word.
 *
 * Operations:
 *  - insert(word)       : O(m) where m = word length
 *  - search(word)       : O(m)
 *  - startsWith(prefix) : O(m)
 *  - delete(word)       : O(m)
 *  - countWordsWithPrefix: O(m + k) where k = matching words
 *  - allWordsWithPrefix  : O(m + total chars in subtree)
 *  - longestCommonPrefix : O(sum of all word lengths)
 *
 * Space Complexity: O(ALPHABET_SIZE × total_chars) ≈ O(26 × n)
 *
 * Applications:
 *  - Autocomplete / typeahead
 *  - Spell checker
 *  - IP routing (longest prefix matching)
 *  - Word games (Scrabble, Boggle)
 */
package DataStructures.trie;

import java.util.*;

public class Trie {

    // ─── Node ─────────────────────────────────────────────────────────────────
    private static class TrieNode {
        TrieNode[] children = new TrieNode[26];
        boolean isEndOfWord;
        int wordCount;          // how many words pass through this node
    }

    // ─── Root ─────────────────────────────────────────────────────────────────
    private final TrieNode root;

    public Trie() { root = new TrieNode(); }

    // ─── Insert ───────────────────────────────────────────────────────────────
    /** Inserts a word. O(m). */
    public void insert(String word) {
        TrieNode cur = root;
        for (char ch : word.toLowerCase().toCharArray()) {
            int idx = ch - 'a';
            if (cur.children[idx] == null) cur.children[idx] = new TrieNode();
            cur = cur.children[idx];
            cur.wordCount++;
        }
        cur.isEndOfWord = true;
    }

    // ─── Search ───────────────────────────────────────────────────────────────
    /** Returns true if exact word exists. O(m). */
    public boolean search(String word) {
        TrieNode node = getNode(word.toLowerCase());
        return node != null && node.isEndOfWord;
    }

    /** Returns true if any word starts with prefix. O(m). */
    public boolean startsWith(String prefix) {
        return getNode(prefix.toLowerCase()) != null;
    }

    private TrieNode getNode(String s) {
        TrieNode cur = root;
        for (char ch : s.toCharArray()) {
            int idx = ch - 'a';
            if (cur.children[idx] == null) return null;
            cur = cur.children[idx];
        }
        return cur;
    }

    // ─── Delete ───────────────────────────────────────────────────────────────
    /** Removes word from trie (if it exists). O(m). */
    public boolean delete(String word) { return deleteRec(root, word.toLowerCase(), 0); }

    private boolean deleteRec(TrieNode cur, String word, int depth) {
        if (cur == null) return false;
        if (depth == word.length()) {
            if (!cur.isEndOfWord) return false;
            cur.isEndOfWord = false;
            return true;
        }
        int idx = word.charAt(depth) - 'a';
        if (!deleteRec(cur.children[idx], word, depth + 1)) return false;
        cur.children[idx].wordCount--;
        if (cur.children[idx].wordCount == 0) cur.children[idx] = null;
        return true;
    }

    // ─── Count words with prefix ─────────────────────────────────────────────
    public int countWordsWithPrefix(String prefix) {
        TrieNode node = getNode(prefix.toLowerCase());
        return node == null ? 0 : node.wordCount;
    }

    // ─── All words with prefix (autocomplete) ────────────────────────────────
    public List<String> autocomplete(String prefix) {
        List<String> results = new ArrayList<>();
        TrieNode node = getNode(prefix.toLowerCase());
        if (node != null) collectWords(node, new StringBuilder(prefix.toLowerCase()), results);
        return results;
    }

    private void collectWords(TrieNode node, StringBuilder path, List<String> results) {
        if (node.isEndOfWord) results.add(path.toString());
        for (int i = 0; i < 26; i++) {
            if (node.children[i] != null) {
                path.append((char)('a' + i));
                collectWords(node.children[i], path, results);
                path.deleteCharAt(path.length() - 1);
            }
        }
    }

    // ─── Longest Common Prefix ────────────────────────────────────────────────
    /** Returns the longest prefix shared by all inserted words. */
    public String longestCommonPrefix() {
        StringBuilder prefix = new StringBuilder();
        TrieNode cur = root;
        while (true) {
            int childCount = 0, nextIdx = -1;
            for (int i = 0; i < 26; i++) {
                if (cur.children[i] != null) { childCount++; nextIdx = i; }
            }
            if (childCount != 1 || cur.isEndOfWord) break;
            prefix.append((char)('a' + nextIdx));
            cur = cur.children[nextIdx];
        }
        return prefix.toString();
    }

    // ─── Print all words ─────────────────────────────────────────────────────
    public void printAll() {
        List<String> all = autocomplete("");
        System.out.println("All words (" + all.size() + "): " + all);
    }

    // ─── Main ─────────────────────────────────────────────────────────────────
    public static void main(String[] args) {
        Trie trie = new Trie();
        String[] words = {"apple", "app", "application", "apply", "apt",
                          "bat", "ball", "banana", "band", "can"};
        for (String w : words) trie.insert(w);

        trie.printAll();

        System.out.println("\nsearch(apple)      : " + trie.search("apple"));
        System.out.println("search(ap)         : " + trie.search("ap"));
        System.out.println("startsWith(app)    : " + trie.startsWith("app"));
        System.out.println("startsWith(xyz)    : " + trie.startsWith("xyz"));
        System.out.println("countWithPrefix(ba): " + trie.countWordsWithPrefix("ba"));
        System.out.println("autocomplete(app)  : " + trie.autocomplete("app"));
        System.out.println("autocomplete(ba)   : " + trie.autocomplete("ba"));

        System.out.println("\n-- Delete 'apple' --");
        trie.delete("apple");
        System.out.println("search(apple) after delete: " + trie.search("apple"));
        System.out.println("search(app)   after delete: " + trie.search("app"));

        System.out.println("\n-- Longest Common Prefix --");
        Trie lcp = new Trie();
        lcp.insert("flower"); lcp.insert("flow"); lcp.insert("flight");
        System.out.println("LCP of {flower, flow, flight}: \"" + lcp.longestCommonPrefix() + "\"");
    }
}
