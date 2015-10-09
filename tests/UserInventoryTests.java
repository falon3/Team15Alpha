public class UserInventoryTests {
  public void testSkillManipulation() {
    Inventory inv = new Inventory();
    Skill skill = new Skill("Skill Name", "category");

    // Testing adding a skill to inventory
    inv.add(skill);
	// test for getting skill from inventory as well
    assertTrue(inv.get(0).equals(skill));

    // Testing modifying a skill in inventory
    Photo dog = new Photo("dog chasing it's tail");
    Skill held_skill = inv.get(0);
    held_skill.setDescription("I jumped and then got really tired");
    held_skill.setPhoto(dog);
    held_skill.setVisibility(false);
    assertTrue(dog.equals(held_skill.getPhoto()));
    assertTrue(held_skill.getDescription().equals("I jumped and then got really tired"));
    assertTrue(held_skill.getVisibility().equals(false));

    // Testing removing a skill from inventory
    inv.remove(skill);
    assertTrue(inv.size()==0);


  }
  public void testSkillSorting() {
    Inventory inv = new Inventory();
    Skill skill = new Skill("Skill Name", "category");
    Skill skill2 = new Skill("Skill Naem", "dategory");

	inv.add(skill2);
	inv.add(skill);

	assertEquals(inv.getSkillsSortedByCategory(), new ArrayList<Skill> { skill, skill2 });
  }
  public void testSearchSkills() {
    Inventory inv = new Inventory();
    Skill skill = new Skill("Skill Name", "category");
    Skill skill2 = new Skill("Skil Naem", "dategory");

	inv.add(skill2);
	inv.add(skill);

	assertEquals(inv.findByName("Name"), new Set<Skill> { skill });
	assertEquals(inv.findByName("Naem"), new Set<Skill> { skill2 });
	assertEquals(inv.findByCategory("category"), new Set<Skill> { skill });
	assertEquals(inv.findByCategory("dategory"), new Set<Skill> { skill2 });
	assertEquals(inv.findByCategory("gory"), new Set<Skill> { skill1, skill2 });
  }
}
