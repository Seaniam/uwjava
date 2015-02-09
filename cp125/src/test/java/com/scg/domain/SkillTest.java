package com.scg.domain;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * @author Sean Carberry
 * @version 4
 * @since 1/19/15
 */
public class SkillTest {
    @Test
    public void testGetSkillType() throws Exception {
        assertEquals("Expect 'Project Manager' to be returned as a string value.", Skill.PROJECT_MANAGER.getSkillType(), "Project Manager");
        assertEquals("Expect 'Software Engineer' to be returned as a string value.", Skill.SOFTWARE_ENGINEER.getSkillType(), "Software Engineer");
        assertEquals("Expect 'Software Tester' to be returned as a string value.", Skill.SOFTWARE_TESTER.getSkillType(), "Software Tester");
        assertEquals("Expect 'System Architect' to be returned as a string value.", Skill.SYSTEM_ARCHITECT.getSkillType(), "System Architect");
        assertEquals("Expect 'Unknown Skill' to be returned as a string value.", Skill.UNKNOWN_SKILL.getSkillType(), "Unknown Skill");
    }
}
