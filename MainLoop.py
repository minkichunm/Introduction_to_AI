# 11 for the selected piece
# 12 for where we can move the selected piece
# 0 for legal emtpy space
# 9 illegal space
# 1-6 players


import pygame as pg
from Board import Board

pg.init()
clock = pg.time.Clock()
running = True
window = pg.display.set_mode((640*2, 480*2))
window.fill((255, 255, 255))
btn = pg.Rect(0, 0, 100, 30)
rect1 = pg.Rect(0, 30, 100, 100)
COLOR1=(0,0,255)
COLOR2=(0,255,255)
COLOR3=(255,255,255)
COLOR4=(0,0,0)
COLOR5=(255,0,0)
COLOR6 = (255,255,0)
players = 2


b = Board(players)

def validMoveWalk(b , x, y):
    if b.getEntry(x+1,y+1) == 0:
        b.setEntry(x+1,y+1, 12)
    if b.getEntry(x-1,y-1) == 0:
        b.setEntry(x-1,y-1, 12)
    if b.getEntry(x+1,y-1) == 0:
        b.setEntry(x+1,y-1, 12)

    if b.getEntry(x-1,y+1) == 0:
        b.setEntry(x-1,y+1, 12)


def validMoveJump(b, x, y):
    #add all players 3-6
    if (b.getEntry(x+1,y+1) == 1) or (b.getEntry(x+1,y+1) == 2):
        if (b.getEntry(x+2,y+2) == 0):
            b.setEntry(x+2,y+2, 12)
            validMoveJump(b,x+2,y+2)
    if (b.getEntry(x-1,y-1) == 1) or (b.getEntry(x-1,y-1) == 2):
        if (b.getEntry(x-2,y-2) == 0):
            b.setEntry(x-2,y-2, 12)
            validMoveJump(b,x-2,y-2)

    if (b.getEntry(x+1,y-1) == 1) or (b.getEntry(x+1,y-1) == 2):
        if (b.getEntry(x+2,y-2) == 0):
            b.setEntry(x+2,y-2, 12)
            validMoveJump(b,x+2,y-2)


    if (b.getEntry(x-1,y+1) == 1) or (b.getEntry(x-1,y+1) == 2):
        if (b.getEntry(x-2,y+2) == 0):
            b.setEntry(x-2,y+2, 12)
            validMoveJump(b,x-2,y+2)
def movePiece(b, x , y):
    b.setEntry(x,y,b.currentPlayer)
    b.resetAfterMoves()
    b.currentPlayerIncrement()
    if b.checkWin():
        print("WIN!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")

rectangleHeight = 50
rectangleWidth = 50

while running:
    clock.tick(60)
    window.fill((255, 255, 255))
    for e in pg.event.get():
        if e.type == pg.MOUSEBUTTONDOWN:
            (mouseX, mouseY) = pg.mouse.get_pos()
            print(mouseY//rectangleWidth,mouseX//rectangleHeight)
            if b.getEntry(mouseY//rectangleWidth,mouseX//rectangleHeight) == b.currentPlayer:
                b.resetPossibleMoves()

                b.setEntry(mouseY//rectangleWidth,mouseX//rectangleHeight, 11)
                validMoveWalk(b,mouseY//rectangleWidth,mouseX//rectangleHeight)
                validMoveJump(b,mouseY//rectangleWidth,mouseX//rectangleHeight)
            elif b.getEntry(mouseY//rectangleWidth,mouseX//rectangleHeight) == 11:
                b.resetPossibleMoves()
            elif b.getEntry(mouseY//rectangleWidth,mouseX//rectangleHeight) == 12:
                movePiece(b,mouseY//rectangleWidth,mouseX//rectangleHeight)


        if e.type == pg.QUIT:
            running = False
    #end event handling
    for i in range(b.length1):
        for j in range(b.length2):
            if b.getEntry(i, j) == 0 :
                pg.draw.rect(window,COLOR1,(j*rectangleHeight,i*rectangleWidth,rectangleHeight,rectangleWidth))

            elif b.getEntry(i, j) == 1:
                pg.draw.rect(window,COLOR2,(j*rectangleHeight,i*rectangleWidth,rectangleHeight,rectangleWidth))

            elif  b.getEntry(i, j) == 2:
                pg.draw.rect(window,COLOR3,(j*rectangleHeight,i*rectangleWidth,rectangleHeight,rectangleWidth))

            elif  b.getEntry(i, j) == 11:
                pg.draw.rect(window,COLOR5,(j*rectangleHeight,i*rectangleWidth,rectangleHeight,rectangleWidth))
            elif  b.getEntry(i, j) == 12:
                pg.draw.rect(window,COLOR6,(j*rectangleHeight,i*rectangleWidth,rectangleHeight,rectangleWidth))
            else:
                pg.draw.rect(window,COLOR4,(j*rectangleHeight,i*rectangleWidth,rectangleHeight,rectangleWidth))

    pg.display.flip()

#end main loop
pg.quit()
