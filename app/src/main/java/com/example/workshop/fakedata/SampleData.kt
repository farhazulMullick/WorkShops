package com.example.workshop.fakedata

import com.example.workshop.database.WorkShopTable

object SampleData {
    fun getSampleData(): List<WorkShopTable> {

        return mutableListOf(
            WorkShopTable(
                0,
                "Machine Learning",
                "Learning Machine Learning from Scratch and take the fast step towards AI",
                "https://trainings.internshala.com/cached_uploads/home/images/machine-learning.jpg"
            ),
            WorkShopTable(
                1,
                "Android App Development",
                "Build your own food ordering app",
                "https://trainings.internshala.com/cached_uploads/home/images/android.jpg"
            ),
            WorkShopTable(
                2,
                "Programming with Python",
                "Build a fantastic cricket game using Python Language",
                "https://trainings.internshala.com/cached_uploads/home/images/python.jpg"
            ),
            WorkShopTable(
                3,
                "Web Development",
                "Learn How to create a website from scratch",
                "https://trainings.internshala.com/cached_uploads/home/images/web-development.jpg"
            ),
            WorkShopTable(
                4,
                "Ethical Hacking",
                "Learn how to hack the secure Web Applications",
                "https://trainings.internshala.com/cached_uploads/home/images/hacking.jpg"
            ),
            WorkShopTable(
                5,
                "Core Java",
                "Develop system applications from scratch",
                "https://trainings.internshala.com/cached_uploads/home/images/java.jpg"
            ),
            WorkShopTable(
                6,
                "React",
                "Master the front-end powerhouse used to build seamless applications!",
                "https://trainings.internshala.com/cached_uploads/home/images/react.jpg"
            ),
            WorkShopTable(
                7,
                "Block Chain",
                "Master the technology that is disrupting banking, IoT, logistics and other industries",
                "https://trainings.internshala.com/cached_uploads/home/images/blockchain.jpg"
            ),
            WorkShopTable(
                8,
                "Angular",
                "Learn Angular and create your own social blogging websites",
                "https://trainings.internshala.com/cached_uploads/home/images/angular.jpg"
            ),
            WorkShopTable(
                9,
                "Data Structures & Algorithms",
                "Master the foundation of datastructures & algorithms to become an ace programmer",
                "https://trainings.internshala.com/cached_uploads/home/images/data-structures-algorithms.jpg"
            ),

            WorkShopTable(
                10,
                "Git & GitHub",
                "Master the most popular version control tool used by developers",
                "https://trainings.internshala.com/cached_uploads/home/images/git.jpg"
            ),


            )
    }

}