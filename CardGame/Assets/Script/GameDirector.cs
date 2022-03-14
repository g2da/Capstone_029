using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class GameDirector : MonoBehaviour
{

    GameObject[] hit_ob = new GameObject[2]; // 뒤집어진 카드 Object 저장
    Sprite[] check_card = new Sprite[2];     //뒤집어진 카드의 이미지 저장

    public int touch_c = 0;

    // Start is called before the first frame update
    void Start()
    {

    }

    public void Count_minus()
    {
        touch_c -= 1;
    }

    // Update is called once per frame
    void Update()
    {
        if (Input.GetMouseButtonDown(0) && touch_c == 0)
        {
            Vector2 pos = Camera.main.ScreenToWorldPoint(Input.mousePosition);
            RaycastHit2D hit = Physics2D.Raycast(pos, Vector2.zero);
            
            hit_ob[touch_c] = hit.transform.gameObject;

            Debug.Log(hit.transform.gameObject.name);

            touch_c += 1;
        }
        else if (Input.GetMouseButtonDown(0) && touch_c == 1)   // 두 장이 선택된 경우
        {
            Vector2 pos = Camera.main.ScreenToWorldPoint(Input.mousePosition);
            RaycastHit2D hit = Physics2D.Raycast(pos, Vector2.zero);

            hit_ob[touch_c] = hit.transform.gameObject;

            touch_c += 1;

            Debug.Log(hit.transform.gameObject.name);

            if (check_card[0] == check_card[1]) //이미지 비교
            {
                touch_c = 0;
            }
            else
            {
                StartCoroutine(hit_ob[0].GetComponent<rotation1>().RotateCard_back());
                StartCoroutine(hit_ob[1].GetComponent<rotation1>().RotateCard_back());

            }
        }



        /*if(Input.GetMouseButtonDown(0) && f_c == 2 && check_card.Count == 2)
        {
            if (check_card[0] == check_card[1])
            {

            }
            else //카드가 같지 않으면
            {

            }
        }*/
    }

    public void selected_Card(Sprite worldImage) //이 함수는 카드를 클릭하면 호출된다. 호출되었을 때 클릭한 카드를 배열에 저장한다.
                                                 //배열에 클릭된 카드 2장을 저장하는 함수
    {
        check_card[touch_c] = worldImage;
    }


}