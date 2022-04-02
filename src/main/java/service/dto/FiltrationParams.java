package service.dto;

import lombok.Getter;
import lombok.Setter;
import model.enums.AstartesCategory;
import model.enums.MeleeWeapon;
import model.enums.Weapon;
import model.xmlLists.Messages;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Getter
public class FiltrationParams {

    public static List<String> FILTRATION_PARAMS = Arrays.asList("name", "minX", "maxX", "minY", "maxY",
        "minHealth", "maxHealth", "category", "weapon", "meleeWeapon", "chapterName", "chapterParLeg",
        "chapterWorld", "chapterMinId", "chapterMaxId", "chapterMinMarCount", "chapterMaxMarCount", "minId", "maxId");

    @Setter
    private Messages messages;

    private String name = "";
    private long minId = Long.MIN_VALUE;
    private long maxId = Long.MAX_VALUE;
    private float minX = Float.MIN_VALUE;
    private float maxX = Float.MAX_VALUE;
    private int minY = Integer.MIN_VALUE;
    private int maxY = Integer.MAX_VALUE;
    private int minHealth = Integer.MIN_VALUE;
    private int maxHealth = Integer.MAX_VALUE;
    private List<AstartesCategory> category = Arrays.asList(AstartesCategory.values());
    private List<Weapon> weapon = Arrays.asList(Weapon.values());
    private List<MeleeWeapon> meleeWeapon = Arrays.asList(MeleeWeapon.values());


    private boolean chapterFiltrationSet = false;

    private String chapterName = "";
    private String chapterParLeg = "";
    private String chapterWorld = "";
    private int chapterMinId = Integer.MIN_VALUE;
    private int chapterMaxId = Integer.MAX_VALUE;
    private int chapterMinMarCount = Integer.MIN_VALUE;
    private int chapterMaxMarCount = Integer.MAX_VALUE;


    public void setParam(String param, String value) {
        switch (param) {
            case "name":
                setName(value);
                break;
            case "minX":
                setMinX(value);
                break;
            case "maxX":
                setMaxX(value);
                break;
            case "minId":
                setMinId(value);
                break;
            case "maxId":
                setMaxId(value);
                break;
            case "minY":
                setMinY(value);
                break;
            case "maxY":
                setMaxY(value);
                break;
            case "minHealth":
                setMinHealth(value);
                break;
            case "maxHealth":
                setMaxHealth(value);
                break;
            case "category":
                setCategory(value);
                break;
            case "weapon":
                setWeapon(value);
                break;
            case "meleeWeapon":
                setMeleeWeapon(value);
                break;
            case "chapterName":
                setChapterName(value);
                chapterFiltrationSet = true;
                break;
            case "chapterParLeg":
                setChapterParLeg(value);
                chapterFiltrationSet = true;
                break;
            case "chapterWorld":
                setChapterWorld(value);
                chapterFiltrationSet = true;
                break;
            case "chapterMinId":
                setChapterMinId(value);
                chapterFiltrationSet = true;
                break;
            case "chapterMaxId":
                setChapterMaxId(value);
                chapterFiltrationSet = true;
                break;
            case "chapterMinMarCount":
                setChapterMinMarCount(value);
                chapterFiltrationSet = true;
                break;
            case "chapterMaxMarCount":
                setChapterMaxMarCount(value);
                chapterFiltrationSet = true;
                break;
        }

    }

    private float setMinFloat(String paramName, String min) {
        if (min == null || min.isEmpty()) {
            messages.addNewMessage(paramName + " не может быть равен null или быть пустым.");
            return Float.MIN_VALUE;
        }

        try {
            return Float.parseFloat(min);
        } catch (NumberFormatException numberFormatException) {
            messages.addNewMessage(paramName + " должен быть вещественным числом.");
            return Float.MIN_VALUE;
        }
    }

    private float setMaxFloat(String paramName, String max) {
        if (max == null || max.isEmpty()) {
            messages.addNewMessage(paramName + " не может быть равен null или быть пустым.");
            return Float.MAX_VALUE;
        }

        try {
            return Float.parseFloat(max);
        } catch (NumberFormatException numberFormatException) {
            messages.addNewMessage(paramName + " должен быть вещественным числом.");
            return Float.MAX_VALUE;
        }
    }


    private int setMin(String paramName, String min) {
        if (min == null || min.isEmpty()) {
            messages.addNewMessage(paramName + " не может быть равен null или быть пустым.");
            return Integer.MIN_VALUE;
        }

        try {
            return Integer.parseInt(min);
        } catch (NumberFormatException numberFormatException) {
            messages.addNewMessage(paramName + " должен быть целым числом.");
            return Integer.MIN_VALUE;
        }
    }

    private int setMax(String paramName, String max) {
        if (max == null || max.isEmpty()) {
            messages.addNewMessage(paramName + " не может быть равен null или быть пустым.");
            return Integer.MAX_VALUE;
        }

        try {
            return Integer.parseInt(max);
        } catch (NumberFormatException numberFormatException) {
            messages.addNewMessage(paramName + " должен быть целым числом.");
            return Integer.MAX_VALUE;
        }
    }

    private String setString(String paramName, String str) {
        if (str == null) {
            messages.addNewMessage(paramName + " не может быть равен null.");

            return  "";
        }

        return str;
    }

    public void setName(String name) {
        this.name = setString("name", name);

    }

    public void setMinX(String minX) {
        this.minX = setMinFloat("minX", minX);
    }

    public void setMaxX(String maxX) {
        this.maxX = setMaxFloat("maxX", maxX);
    }

    public void setMinY(String minY) {
        this.minY = setMin("minY", minY);
    }

    public void setMaxY(String maxY) {
        this.maxY = setMax("maxY", maxY);
    }

    public void setMinId(String minId) {
        this.minId = setMin("minId", minId);
    }

    public void setMaxId(String maxId) {
        this.maxId = setMax("maxId", maxId);
    }


    public void setMinHealth(String minHealth) {
        this.minHealth = setMin("minHealth", minHealth);
    }

    public void setMaxHealth(String maxHealth) {
        this.maxHealth = setMax("maxHealth", maxHealth);
    }

    public void setCategory(String category) {
        final String s = setString("category", category);

        try {
            AstartesCategory cat = AstartesCategory.valueOf(s);

            if (cat == AstartesCategory.NONE) {
                this.category = Arrays.asList(AstartesCategory.values());
            } else {
                this.category = Collections.singletonList(cat);
            }

        } catch (Exception ex) {
            messages.addNewMessage("Не удалось определить значение category");

            this.category = Arrays.asList(AstartesCategory.values());
        }
    }

    public void setWeapon(String weapon) {
        final String s = setString("weapon", weapon);

        try {
            Weapon weap = Weapon.valueOf(s);
            if (weap == Weapon.NONE) {
                this.weapon = Arrays.asList(Weapon.values());
            } else {
                this.weapon = Collections.singletonList(weap);
            }

        } catch (Exception ex) {
            messages.addNewMessage("Не удалось определить значение weapon");
            this.weapon = Arrays.asList(Weapon.values());
        }
    }

    public void setMeleeWeapon(String meleeWeapon) {
        final String s = setString("meleeWeapon", meleeWeapon);

        try {
            MeleeWeapon weapon = MeleeWeapon.valueOf(s);

            if (weapon == MeleeWeapon.NONE) {
                this.meleeWeapon = Arrays.asList(MeleeWeapon.values());
            } else {
                this.meleeWeapon = Collections.singletonList(weapon);
            }

        } catch (Exception ex) {
            this.meleeWeapon = Arrays.asList(MeleeWeapon.values());
            messages.addNewMessage("Не удалось определить значение meleeWeapon");
        }
    }

    public void setChapterName(String chapterName) {
        this.chapterName = setString("chapterName", chapterName);

    }

    public void setChapterParLeg(String chapterParLeg) {
        this.chapterParLeg = setString("chapterParLeg", chapterParLeg);

    }

    public void setChapterWorld(String chapterWorld) {
        this.chapterWorld = setString("chapterWorld", chapterWorld);

    }

    public void setChapterMinId(String chapterMinId) {
        this.chapterMinId = setMin("chapterMinId", chapterMinId);
    }

    public void setChapterMaxId(String chapterMaxId) {
        this.chapterMaxId = setMax("chapterMaxId", chapterMaxId);
    }

    public void setChapterMinMarCount(String chapterMinMarCount) {
        this.chapterMinMarCount = setMin("chapterMinMarCount", chapterMinMarCount);
    }

    public void setChapterMaxMarCount(String chapterMaxMarCount) {
        this.chapterMaxMarCount = setMax("chapterMaxMarCount",chapterMaxMarCount);
    }
}
