package com.letiproject.foodplanner.app.web.util.validation;

import net.jpountz.xxhash.XXHashFactory;
import org.mindrot.jbcrypt.BCrypt;

import java.io.UnsupportedEncodingException;

/**
 * Used to Validate and Generate Hashes (for password as example)
 */
public class StringHashFactory {

    private static final String EMPTY_HASH = "";
    private static final XXHashFactory hashFactory = XXHashFactory.fastestInstance();
    private static final long SEED = 0x57a1cc61;
    private static final String ENCODING = "UTF-8";

    public static Long generateHashSimple(String value, HashType type) {
        try {
            byte[] data = value.getBytes(ENCODING);
            return hashFactory.hash64().hash(data, 0, data.length, SEED);
        } catch (UnsupportedEncodingException e) {
            return 0L;
        }
    }

    public static String generateHashCrypto(String value, HashType type) {
        return (value == null || value.isEmpty())
                ? EMPTY_HASH
                : BCrypt.hashpw(value, BCrypt.gensalt(type.getValue()));
    }

    public static boolean validateHashCrypto(String candidate, String hash) throws NullPointerException {
        if (candidate == null || candidate.isEmpty())
            throw new NullPointerException("CANDIDATE");

        if (hash == null || hash.isEmpty())
            throw new NullPointerException("HASH");

        return BCrypt.checkpw(candidate, hash);
    }

    // Values are SALT used for hash generation
    public enum HashType {
        COMMON(11),
        PASS(17),
        EMAIL(13);

        private final int value;

        HashType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
