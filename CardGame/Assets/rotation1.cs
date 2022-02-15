//카드 회전 담당 스크립트

using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.EventSystems;

public class rotation1 : MonoBehaviour
{
    private SpriteRenderer rend; //이미지를 표현하는 속성을 가진 객체
    CardInfo thisCard;

    [SerializeField] //private 변수를 inspector에서 접근가능하게 해줌
    private Sprite faceSprite, backSprite;

    private bool coroutineAllowed, facedUp;

    void Start()
    {
        gameObject.AddComponent<CardInfo>();
        rend = GetComponent<SpriteRenderer>();
        thisCard = GetComponent<CardInfo>();

        rend.sprite = backSprite;
        coroutineAllowed = true;
        facedUp = false;
        

    }



    private void OnMouseDown()
    {
        if (coroutineAllowed)
        {
            StartCoroutine(RotateCard());
        }
    }


    private IEnumerator RotateCard()
    {
        coroutineAllowed = false;
        if (!facedUp)
        {
            for (float i = 0f; i <= 180f; i += 10f)
            {
                transform.rotation = Quaternion.Euler(0f, i, 0f);
                if (i == 90f)
                {
                    rend.sprite = faceSprite;
                    thisCard.cardStatus = 1;

                    GameObject director = GameObject.Find("GameDirector");
                    director.GetComponent<GameDirector>().Flip_Count();
                    

                }
                yield return new WaitForSeconds(0.01f);
            }
        }

        else if (facedUp)
        {
            for (float i = 180f; i >= 0f; i -= 10f)
            {
                transform.rotation = Quaternion.Euler(0f, i, 0f);
                if (i == 90f)
                {
                    rend.sprite = backSprite;
                    thisCard.cardStatus = 0;
                    
                }
                yield return new WaitForSeconds(0.01f);
            }
        }
        coroutineAllowed = true;

        facedUp = !facedUp;
    }

    // Update is called once per frame

}
