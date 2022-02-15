//게임 시작시 프리팹을 사용한 게임 빌드 스크립트

using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class BuildGame : MonoBehaviour
{
    int number = 3;
    float px = -3.0f;
    float py = 2.7f;

    public GameObject cardPrefab;
    // Start is called before the first frame update
    void Start()
    {
        buildgame();
    }

    // Update is called once per frame
    void Update()
    {
        
    }

    private void buildgame()
    {
        float px1 = px;

        for (int j = 0; j < 2; j++)
        {
            float py1 = py;

            for (int i = 0; i < number; i++)
            {
                GameObject card = Instantiate(cardPrefab) as GameObject;
                card.transform.position = new Vector3(px1, py1, 0);
                py1 -= 2.7f;
            }
            px1 += 4.6f;
        }

        float py2 = 1.35f;
        float px2 = px + 2.3f;
        for (int i = 0; i < number - 1; i++)
        {
            GameObject card = Instantiate(cardPrefab) as GameObject;
            card.transform.position = new Vector3(px2, py2, 0);
            py2 -= 2.7f;
        }
    }
}
