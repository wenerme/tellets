package me.wener.telletsj.data.cq;

import me.wener.telletsj.data.Category;
import me.wener.telletsj.data.Tag;
import org.junit.Before;
import org.junit.Test;

public class TagAndCategoryTest
{
    private CQDataService service;

    @Before
    public void setup()
    {
        service = new CQDataService();
    }

    @Test
    public void testCategory()
    {
        Category game = service.findOrCreateCategory("game");
        game.alias("Game");
        game.alias("游戏");
        service.store(game);

        assert game.equals(service.findCategory("Game"));
        assert game.equals(service.findCategory("游戏"));

        assert service.findCategory("LOL") == null;
        Category lol = service.findOrCreateCategory("LOL").alias("英雄联盟").setParent(game);
        service.store(lol);

        assert service.getChildrenOf(game).iterator().next().equals(lol);
        assert service.getChildrenOf(game).size() == 1;
        assert service.getChildrenOf(game).containsAll(service.getChildrenRecursiveOf(game));
        assert service.getRootCategories().size() == 1;
        assert service.getRootCategories().iterator().next().equals(game);
    }

    @Test
    public void testTag()
    {
        assert service.findTag("Food") == null;
        Tag food = service.findOrCreateTag("Food");
        assert food.getName().equals("Food");
        food.alias("food").alias("食物");
        service.store(food);
        assert food.equals(service.findTag("食物"));
        assert service.getTags().size() == 1;
    }
}
