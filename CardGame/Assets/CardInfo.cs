//카드 정보를 담을 클래스 스크립트

using System.Collections;
using System.Collections.Generic;
using UnityEngine;
[System.Serializable]

public class CardInfo : MonoBehaviour
{
    public string cardName;
    public int cardStatus =0;
    public Sprite wordImage;

    public CardInfo()
    {
        
    }

    public CardInfo(string CardName,Sprite WordImage)
    {
        cardName = CardName;
        wordImage = WordImage;

    }
}

