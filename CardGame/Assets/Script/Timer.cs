using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Timer : MonoBehaviour
{

    public GameObject timerBar;
    public GameObject timerGage;

    GameObject timer;

    

    void Start()
    {
        buildTimer();

    }

    // Update is called once per frame
    void Update()
    {

        if (timer.transform.localScale.x > 0)
        {
            timer.transform.localScale -= new Vector3(0.00003f, 0, 0); // x축 조절로 시간 조절
        }
        else
        {
            timer.SetActive(false);
        }
    }

    void buildTimer()
    {
        timer = Instantiate(timerBar) as GameObject;
        timer.transform.SetParent(timerGage.transform);

        timer.transform.position = new Vector3(-3.8f, 3.95f,0);
        timer.transform.localScale = new Vector2(0.28f, 0.35f);
    }

    void generateTimer()
    {

    }


}
