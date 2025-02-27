package net.spell_engine.api.spell;

import java.util.ArrayList;
import java.util.List;

public class SpellContainer { public SpellContainer() { }
    public String pool;
    public int max_spell_count = 1;
    public List<String> spell_ids;

    public SpellContainer(String pool, int max_spell_count, List<String> spell_ids) {
        this.pool = pool;
        this.max_spell_count = max_spell_count;
        this.spell_ids = spell_ids;
    }

    // MARK: Helpers

    public int cappedIndex(int selected) {
        if (spell_ids.isEmpty()) { return 0; }
        var remainder = selected % spell_ids.size();
        return (remainder >= 0) ? remainder : (remainder + spell_ids.size());
    }

    public String spellId(int selected) {
        if (spell_ids == null || spell_ids.isEmpty()) {
            return null;
        }
        var index = cappedIndex(selected);
        return spell_ids.get(index);
    }

    public boolean isValid() {
        return max_spell_count > 0 && spell_ids != null
                // Valid pool (staves) or non-empty spell list (wands)
                && ( (pool != null && !pool.isEmpty()) || !spell_ids.isEmpty() );
    }

    public boolean isUsable() {
        return isValid() && !spell_ids.isEmpty();
    }

    public SpellContainer copy() {
        return new SpellContainer(pool, max_spell_count, new ArrayList<>(spell_ids));
    }
}
