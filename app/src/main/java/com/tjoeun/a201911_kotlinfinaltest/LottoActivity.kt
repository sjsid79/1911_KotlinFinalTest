package com.tjoeun.a201911_kotlinfinaltest

import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_lotto.*
import java.util.*
import kotlin.collections.ArrayList

class LottoActivity : BaseActivity() {

    var mHandler = Handler()
    var isNowBuying = false


    var usedMoney = 0L
    var luckyMoney = 0L
    var firstRankCount = 0
    var secondRankCount = 0
    var thirdRankCount = 0
    var fourthRankCount = 0
    var fifthRankCount = 0
    var wrongCount = 0

    var lottoNumArrayList = ArrayList<Int>()
    var bonusNum = 0
    var thisWeekLottoNumTextViewArrayList = ArrayList<TextView>()

    var myNumArrayList = ArrayList<Int>()
    var myTextViewArrayList = ArrayList<TextView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lotto)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        autoLottoBtn.setOnClickListener {
            if (!isNowBuying) {
                doLottoLoop()
                isNowBuying = true
                autoLottoBtn.text = "구매 중단"
            }
            else {

                stopLottoLoop()
                isNowBuying = false
                autoLottoBtn.text = "자동 구매 재개"
            }

        }

        buyOneLottoBtn.setOnClickListener {
            setThisWeekLottoNum()
            checkLottoRank()

            usedMoney += 1000
            usedMoneyTxt.text = String.format("사용금액 : %,d원", usedMoney)
        }

    }

    fun checkLottoRank() {

        var correctCount = 0
        for (myNum in myNumArrayList) {

            for (thisWeekNum in lottoNumArrayList) {
                if (myNum == thisWeekNum) {
                    correctCount++
                }
            }
        }

        if (correctCount == 6) {
            luckyMoney += 2000000000
            firstRankCount++
        }
        else if (correctCount == 5) {

            var isSecondRank = false
            for (num in myNumArrayList) {
                if (num == bonusNum) {
                    isSecondRank = true
                    break
                }
            }

            if (isSecondRank) {
                luckyMoney += 65000000
                secondRankCount++
            }
            else {
                luckyMoney += 1500000
                thirdRankCount++
            }

        }
        else if (correctCount == 4) {
            luckyMoney += 50000
            fourthRankCount++
        }
        else if (correctCount == 3) {
            usedMoney -= 5000
            fifthRankCount++
        }
        else {
            luckyMoney += 0
            wrongCount++
        }

        luckyMoneyTxt.text = String.format("누적 당첨 금액 : %,d원", luckyMoney)
        firstRankCountTxt.text = String.format("1등 당첨 : %,d회", firstRankCount)
        secondRankCountTxt.text = String.format("2등 당첨 : %,d회", secondRankCount)
        thirdRankCountTxt.text = String.format("3등 당첨 : %,d회", thirdRankCount)
        fourthRankCountTxt.text = String.format("4등 당첨 : %,d회", fourthRankCount)
        fifthRankCountTxt.text = String.format("5등 당첨 : %,d회", fifthRankCount)
        wrongCountTxt.text = String.format("낙첨 횟수 : %,d회", wrongCount)

    }

    var lottoRunnable = object : Runnable {
        override fun run() {
            if (usedMoney < 100000000) {
                setThisWeekLottoNum()
                checkLottoRank()
                usedMoney += 1000
                usedMoneyTxt.text = String.format("사용금액 : %,d원", usedMoney)
                doLottoLoop()
            }
            else {
                runOnUiThread {
                    Toast.makeText(mContext, "로또 구매를 종료합니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    fun doLottoLoop() {

        mHandler.post(lottoRunnable)
    }

    fun stopLottoLoop() {
        mHandler.removeCallbacks(lottoRunnable)
    }

    fun setThisWeekLottoNum() {

        lottoNumArrayList.clear()

        for (lottoNumTxt in thisWeekLottoNumTextViewArrayList) {

            var randomNum = 0

            while (true) {
                randomNum = (Math.random() * 45 + 1).toInt()

                var isDuplOk = true

                for (num in lottoNumArrayList) {
                    if (num == randomNum) {
                        isDuplOk = false
                        break
                    }
                }

                if (isDuplOk) {
                    lottoNumArrayList.add(randomNum)
                    break
                }
            }
        }

        Collections.sort(lottoNumArrayList)

        while (true) {

            var tempRandomNum = (Math.random() * 45 + 1).toInt()
            var isDuplOk = true

            for (num in lottoNumArrayList) {
                if (tempRandomNum == num) {
                    isDuplOk = false
                    break
                }
            }

            if (isDuplOk) {
                bonusNum = tempRandomNum
                break
            }

        }


        for (i in 0..lottoNumArrayList.size - 1) {
            var numTxt = thisWeekLottoNumTextViewArrayList.get(i)
            var number = lottoNumArrayList.get(i)

            numTxt.text = number.toString()
        }

        bonusNumTxt.text = bonusNum.toString()

    }

    override fun setValues() {
        thisWeekLottoNumTextViewArrayList.add(lottoNumTxt1)
        thisWeekLottoNumTextViewArrayList.add(lottoNumTxt2)
        thisWeekLottoNumTextViewArrayList.add(lottoNumTxt3)
        thisWeekLottoNumTextViewArrayList.add(lottoNumTxt4)
        thisWeekLottoNumTextViewArrayList.add(lottoNumTxt5)
        thisWeekLottoNumTextViewArrayList.add(lottoNumTxt6)

        myTextViewArrayList.add(myLottoNumTxt1)
        myTextViewArrayList.add(myLottoNumTxt2)
        myTextViewArrayList.add(myLottoNumTxt3)
        myTextViewArrayList.add(myLottoNumTxt4)
        myTextViewArrayList.add(myLottoNumTxt5)
        myTextViewArrayList.add(myLottoNumTxt6)

        for (myTv in myTextViewArrayList) {
            myNumArrayList.add(myTv.text.toString().toInt())
        }
    }
}
