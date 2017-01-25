/*
 * Copyright 2016 Microprofile.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.nosoftskills.predcomposer.browser.fixtures;

import com.nosoftskills.predcomposer.model.Game;
import com.nosoftskills.predcomposer.model.User;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static com.nosoftskills.predcomposer.user.PasswordHashUtil.hashPassword;

@Singleton
@Startup
public class TestDataInserter {

    @PersistenceContext
    private EntityManager entityManager;

    @PostConstruct
    public void insertTestData() {
        User user1 = new User("ivan", hashPassword("ivan"), "ivan@example.com", "Ivan", "Ivanov", true);
        User user2 = new User("koko", hashPassword("koko"), "koko@example.com", "Koko", "Stefanov", false);

        LocalDate tomorrow = LocalDate.now().plusDays(1);
        LocalDateTime gameTime = LocalDateTime.of(tomorrow, LocalTime.of(21, 45));
        Game game1 = new Game("Barcelona", "Bayern Munchen", gameTime);
        Game game2 = new Game("Juventus", "Chelsea", gameTime);
        entityManager.persist(user1);
        entityManager.persist(user2);
        entityManager.persist(game1);
        entityManager.persist(game2);
    }

}