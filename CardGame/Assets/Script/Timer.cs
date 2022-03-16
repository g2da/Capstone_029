﻿using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class Timer : MonoBehaviour
{

    public GameObject timerBar;
    public GameObject timerGage;
    public GameObject canvas;

    GameObject timer;
    

    void Start()
    {
    
        buildTimer();


    }

    // Update is called once per frame
    void Update()
    {

        if (timer.transform.localScale.y > 0)
        {
            timer.transform.localScale -= new Vector3(0, 0.00005f, 0); // x축 조절로 시간 조절
        }
        else
        {
            timer.SetActive(false);
            SceneManager.LoadScene("GameOver");
        }
    }

    void buildTimer()
    {
        timer = Instantiate(timerBar) as GameObject;
        timer.transform.SetParent(timerGage.transform);
        timerGage.SetActive(true);
        timerGage.transform.position = new Vector3(-9f, -4f, 0);
        timerGage.transform.localScale = new Vector2(0.35f, 0.35f);
        timer.transform.position = new Vector3(-9f, -3.9f,0);
        timer.transform.localScale = new Vector2(0.8f, 1f);
    }



}
