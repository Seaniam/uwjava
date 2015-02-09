package com.scg.domain;

/**
 * Encapsulates a billable skill.
 *
 * @author Sean Carberry
 * @version 4
 * @since 1/12/15
 */
public enum Skill {
    PROJECT_MANAGER("Project Manager", 250),
    SOFTWARE_ENGINEER("Software Engineer", 150),
    SOFTWARE_TESTER("Software Tester", 100),
    SYSTEM_ARCHITECT("System Architect", 200),
    UNKNOWN_SKILL("Unknown Skill", 0);

    /** rate property */
    private int rate;
    /** skill type property */
    private String skillType;

    /**
     * Creates a specified skill
     * @param skillType skill type
     */
    Skill(String skillType, int rate) {
        this.skillType = skillType;
        this.rate = rate;
    }

    /**
     * Gets the skill type.
     *
     * @return the skill type in string format.
     */
    public String getSkillType() {
        return skillType;
    }

    /**
     * Gets the skill rate.
     *
     * @return the skill rate in string format.
     */
    public int getRate() {
        return rate;
    }
}
