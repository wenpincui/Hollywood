package com.splunk.hollywood.service;

import com.splunk.hollywood.exception.NotSupportException;
import com.typesafe.config.Config;
import org.grouplens.lenskit.ItemRecommender;
import org.grouplens.lenskit.ItemScorer;
import org.grouplens.lenskit.baseline.BaselineScorer;
import org.grouplens.lenskit.baseline.ItemMeanRatingItemScorer;
import org.grouplens.lenskit.baseline.UserMeanBaseline;
import org.grouplens.lenskit.baseline.UserMeanItemScorer;
import org.grouplens.lenskit.core.LenskitConfiguration;
import org.grouplens.lenskit.core.LenskitRecommender;
import org.grouplens.lenskit.core.LenskitRecommenderEngine;
import org.grouplens.lenskit.data.dao.EventDAO;
import org.grouplens.lenskit.data.dao.SimpleFileRatingDAO;
import org.grouplens.lenskit.knn.item.ItemItemScorer;
import org.grouplens.lenskit.scored.ScoredId;
import org.grouplens.lenskit.transform.normalize.BaselineSubtractingUserVectorNormalizer;
import org.grouplens.lenskit.transform.normalize.UserVectorNormalizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
public class RecommendService {
    private File inputFile = new File("data/small/ratings2.csv");

    private LenskitRecommenderEngine engine;
    private Config config;

    @PostConstruct
    public void setup() throws Exception {
        if (config.getBoolean("hollywood.recommendEnable")) {
            LenskitConfiguration config = new LenskitConfiguration();
            // Use item-item CF to score items
            config.bind(ItemScorer.class)
                    .to(ItemItemScorer.class);
            // let's use personalized mean rating as the baseline/fallback predictor.
            // 2-step process:
            // First, use the user mean rating as the baseline scorer
            config.bind(BaselineScorer.class, ItemScorer.class)
                    .to(UserMeanItemScorer.class);
            // Second, use the item mean rating as the base for user means
            config.bind(UserMeanBaseline.class, ItemScorer.class)
                    .to(ItemMeanRatingItemScorer.class);
            // and normalize ratings by baseline prior to computing similarities
            config.bind(UserVectorNormalizer.class)
                    .to(BaselineSubtractingUserVectorNormalizer.class);

            config.bind(EventDAO.class).to(new SimpleFileRatingDAO(inputFile, ","));

            engine = LenskitRecommenderEngine.build(config);
        }
    }

    public List<Integer> recommend(int userId, int num) throws Exception {
        if (config.getBoolean("hollywood.recommendEnable")) {
            LenskitRecommender rec = engine.createRecommender();

            ItemRecommender irec = rec.getItemRecommender();
            List<ScoredId> recommendations = irec.recommend(userId, num);

            List<Integer> movieIds = new ArrayList<Integer>();

            for (ScoredId recommend : recommendations) {
                movieIds.add((int) recommend.getId());
            }

            return movieIds;
        } else {
            throw new NotSupportException();
        }
    }

    @Autowired
    public void setConfig(Config config) {
        this.config = config;
    }
}
